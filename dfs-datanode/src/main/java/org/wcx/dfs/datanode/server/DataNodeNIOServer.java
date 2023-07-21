package org.wcx.dfs.datanode.server;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;


import static org.wcx.dfs.datanode.server.DataNodeConfig.*;

/**
 * @author wangchengxi
 * @date 2023/7/17 15:33
 */
public class DataNodeNIOServer extends Thread {
    private Selector selector;
    private List<LinkedBlockingQueue<SelectionKey>> queues =
            new ArrayList<>();
    private Map<String, CachedImage> cachedImages = new HashMap<>();


    public DataNodeNIOServer() {
        ServerSocketChannel serverSocketChannel = null;

        try {
            selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(NIO_PORT), 100);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            for (int i = 0; i < 3; i ++) {
                queues.add(new LinkedBlockingQueue<>());
            }

            for (int i = 0; i < 3; i ++) {
                new Worker(queues.get(i)).start();
            }

            System.out.println("NIOServer已经启动，开始监听端口: " + NIO_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = (SelectionKey) keyIterator.next();
                    keyIterator.remove();
                    handleRequest(key);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private void handleRequest(SelectionKey key) throws IOException {
        SocketChannel channel = null;
        try {
            if (key.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                channel = serverSocketChannel.accept();
                if (channel != null) {
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                }
            } else if (key.isReadable()) {
                channel = (SocketChannel) key.channel();
                String remoteAddr = channel.getRemoteAddress().toString();
                int queueIndex = remoteAddr.hashCode() % queues.size();
                queues.get(queueIndex).put(key);

            }
        } catch (Throwable t) {
            t.printStackTrace();
            if (channel != null) {
                channel.close();
            }
        }

    }


    class Worker extends Thread {
        private LinkedBlockingQueue<SelectionKey> queue;

        public Worker(LinkedBlockingQueue<SelectionKey> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                SocketChannel channel = null;

                try {
                    SelectionKey key = queue.take();
                    channel = (SocketChannel) key.channel();
                    if (!channel.isOpen()) {
                        channel.close();
                        continue;
                    }
                    String remoteAddr = channel.getRemoteAddress().toString();

                    ByteBuffer buffer = ByteBuffer.allocate(10 * 1024);

                    //从请求中解析文件名
                    String filename = getFilename(channel, buffer);
                    if (filename == null) {
                        continue;
                    }
                    //从请求中解析文件的大小
                    long imageLength = getImageLength(channel, buffer);
                    //定义已经读取到的文件大小
                    long hasReadImageLength = getHasReadImageLength(channel);

                    //构建针对本地文件的输出流
                    FileOutputStream imageOut = new FileOutputStream(filename);
                    FileChannel imageChannel = imageOut.getChannel();
                    imageChannel.position(imageChannel.size());

                    //如果是第一次接受到请求，就应该把buufer里剩余的数据写入到文件里去
                    if(!cachedImages.containsKey(remoteAddr)) {
                        hasReadImageLength += imageChannel.write(buffer);
                        buffer.clear();
                    }

                    //循环不断地从channel里读取数据，并写入磁盘文件
                    int len = -1;
                    while ((len = channel.read(buffer)) > 0) {
                        hasReadImageLength += len;
                        buffer.flip();
                        imageChannel.write(buffer);
                        buffer.clear();
                    }
                    if (cachedImages.get(remoteAddr) != null) {
                        if (hasReadImageLength == cachedImages.get(remoteAddr).hasReadImageLength) {
                            channel.close();
                            continue;
                        }
                    }
                    imageChannel.close();
                    imageOut.close();

                    // 判断一下，如果已经读取完毕，就返回一个成功给客户端
                    if (hasReadImageLength == imageLength) {
                        ByteBuffer outBuffer = ByteBuffer.wrap("SUCCESS".getBytes());
                        channel.write(outBuffer);
                        cachedImages.remove(remoteAddr);
                        System.out.println("文件读取完毕，返回响应给客户端: " + remoteAddr);
                    }
                    // 如果一个文件没有读完，缓存起来，等待下一次读取
                    else {
                        CachedImage cachedImage = new CachedImage(filename, imageLength, hasReadImageLength);
                        cachedImages.put(remoteAddr, cachedImage);
                        key.interestOps(SelectionKey.OP_READ);
                        System.out.println("文件没有读取完毕，等待下一次OP_READ请求，缓存文件: " + cachedImage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (channel != null) {
                        try {
                            channel.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private String getFilename(SocketChannel channel, ByteBuffer buffer) throws Exception {
        String filename = null;
        String remoteAddr = channel.getRemoteAddress().toString();

        if (cachedImages.containsKey(remoteAddr)) {
            filename = cachedImages.get(remoteAddr).filename;
        } else {
            filename = getFilenameFromChannel(channel, buffer);
            if (filename == null) {
                return null;
            }

            String[] filenameSplited = filename.split("/");
            String dirPath = DATA_DIR;

            for (int i = 0; i < filenameSplited.length - 1; i ++) {
                if (i == 0) {
                    continue;
                }
                dirPath += "\\" + filenameSplited[i];
            }

            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            filename = dirPath + "\\" + filenameSplited[filenameSplited.length - 1];
        }
        return filename;
    }


    /**
     * 从网络请求中获取文件名
     * @param channel
     * @param buffer
     * @return
     * @throws Exception
     */
    private String getFilenameFromChannel(SocketChannel channel, ByteBuffer buffer) throws Exception {
        int len = channel.read(buffer);
        if (len > 0) {
            //读取前4个字节获取文件名长度
            byte[] filenameLengthBytes = new byte[4];
            buffer.flip();
            buffer.get(filenameLengthBytes, 0, 4);
            ByteBuffer filenameLengthBuffer = ByteBuffer.allocate(4);
            filenameLengthBuffer.put(filenameLengthBytes);
            filenameLengthBuffer.flip();

            //读取文件名
            int filenameLength = filenameLengthBuffer.getInt();
            byte[] filenameBytes = new byte[filenameLength];
            buffer.get(filenameBytes, 0, filenameLength);
            String filename = new String(filenameBytes);
            return filename;
        }
        return null;
    }

    private Long getImageLength(SocketChannel channel, ByteBuffer buffer) throws Exception {
        Long imageLength = 0L;
        String remoteAddr = channel.getRemoteAddress().toString();

        if (cachedImages.containsKey(remoteAddr)) {
            imageLength = cachedImages.get(remoteAddr).imageLength;
        } else {
            byte[] imageLengthBytes = new byte[8];
            buffer.get(imageLengthBytes, 0, 8);

            ByteBuffer imageLengthBuffer = ByteBuffer.allocate(8);
            imageLengthBuffer.put(imageLengthBytes);
            imageLengthBuffer.flip();
            imageLength = imageLengthBuffer.getLong();
        }

        return imageLength;
    }

    private Long getHasReadImageLength(SocketChannel channel) throws Exception {
        long hasReadImageLength = 0;
        String remoteAddr = channel.getRemoteAddress().toString();
        if (cachedImages.containsKey(remoteAddr)) {
            hasReadImageLength = cachedImages.get(remoteAddr).hasReadImageLength;
        }
        return hasReadImageLength;
    }


    class CachedImage {
        String filename;
        long imageLength;
        long hasReadImageLength;

        public CachedImage(String filename, long imageLength, long hasReadImageLength) {
            this.filename = filename;
            this.imageLength = imageLength;
            this.hasReadImageLength = hasReadImageLength;
        }

        @Override
        public String toString() {
            return "CachedImage{" +
                    "filename='" + filename + '\'' +
                    ", imageLength=" + imageLength +
                    ", hasReadImageLength=" + hasReadImageLength +
                    '}';
        }
    }

}
