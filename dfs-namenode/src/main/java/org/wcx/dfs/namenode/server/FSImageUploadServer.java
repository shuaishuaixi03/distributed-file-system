package org.wcx.dfs.namenode.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author wcx
 * @date 2023/6/5 16:45
 */
public class FSImageUploadServer extends Thread {
    private Selector selector;

    public FSImageUploadServer() {
        this.init();
    }

    private void init() {
        ServerSocketChannel serverSocketChannel = null;
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(9000));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("FSImageUploadServer启动，监听9000端口......");

        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> keysIterator = selector.selectedKeys().iterator();
                while (keysIterator.hasNext()) {
                    SelectionKey key = (SelectionKey) keysIterator.next();
                    keysIterator.remove();
                    try {
                        handleRequest(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
             } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private void handleRequest(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            handleConnectRequest(key);
        } else if (key.isReadable()) {
            handleReadableRequest(key);
        } else if (key.isWritable()) {
            handleWritableRequest(key);
        }
    }

    /**
     * 处理BackupNode连接请求
     * @param key
     * @throws IOException
     */
    private void handleConnectRequest(SelectionKey key) throws IOException {
        SocketChannel channel = null;
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            channel = serverSocketChannel.accept();
            if (channel != null) {
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_READ);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (channel != null) {
                channel.close();
            }
        }
    }

    /**
     * 处理发送fsimage文件的请求
     * @param key
     * @throws IOException
     */
    private void handleReadableRequest(SelectionKey key) throws IOException {
        SocketChannel channel = null;
        try {
            //先删除上一次的fsimage文件
            String fsimageFilePath = "D:\\dfs\\backupnode\\fsimage.mete";

            RandomAccessFile fsimageImageRAF = null;
            FileOutputStream fsimageOut = null;
            FileChannel fsimageFileChannel = null;


            try {
                channel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                int total = 0;
                int count = -1;

                if ((count = channel.read(buffer)) > 0) {
                    File file = new File(fsimageFilePath);
                    if (file.exists()) {
                        file.delete();
                    }

                    fsimageImageRAF = new RandomAccessFile(fsimageFilePath, "rw");
                    fsimageOut = new FileOutputStream(fsimageImageRAF.getFD());
                    fsimageFileChannel = fsimageOut.getChannel();

                    total += count;

                    buffer.flip();
                    fsimageFileChannel.write(buffer);
                    buffer.clear();
                } else {
                    channel.close();
                }

                while ((count = channel.read(buffer)) > 0) {
                    total += count;
                    buffer.flip();
                    fsimageFileChannel.write(buffer);
                    buffer.clear();
                }
                if (total > 0) {
                    System.out.println("接受fsimage文件以及写入本地磁盘完毕......");
                    fsimageFileChannel.force(false);
                    channel.register(selector, SelectionKey.OP_WRITE);
                }
            } finally {
                if (fsimageOut != null) {
                    fsimageOut.close();
                }
                if (fsimageImageRAF != null) {
                    fsimageImageRAF.close();
                }
                if (fsimageFileChannel != null) {
                    fsimageFileChannel.close();
                }
            }

            channel.register(selector, SelectionKey.OP_WRITE);
        } catch (Exception e) {
            e.printStackTrace();
            if (channel != null) {
                channel.close();
            }
        }
    }

    /**
     * 处理返回响应给BackupNode
     * @param key
     * @throws IOException
     */
    private void handleWritableRequest(SelectionKey key) throws IOException {
        SocketChannel channel = null;
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("SUCCESS".getBytes());
            buffer.flip();

            channel = (SocketChannel) key.channel();
            channel.write(buffer);

            System.out.println("fsimage文件上传完毕，返回SUCCESS响应给backupnode......");

            channel.register(selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
            if (channel != null) {
                channel.close();
            }
        }
    }
}
