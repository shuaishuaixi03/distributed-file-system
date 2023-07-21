package org.wcx.dfs.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 负责和数据节点Datanode进行网络通信
 * @author wangchengxi
 * @date 2023/7/20 9:47
 */
public class NIOClient {

    /**
     * 发送一个文件
     * @param hostname
     * @param nioPort
     * @param file
     * @param fileSize
     */
    public static void sendFile(String hostname, int nioPort,
            byte[] file, String filename, long fileSize) {
        //建立一次短连接，发送完数据后断开
        SocketChannel channel = null;
        Selector selector = null;
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(hostname, nioPort));
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_CONNECT);

            System.out.println("主机: " + hostname + "，端口号: " + nioPort + "，成功建立连接，开始传输数据");

            boolean sending = true;

            while (sending) {
                selector.select();
                Iterator<SelectionKey> keysIterator = selector.selectedKeys().iterator();

                while(keysIterator.hasNext()) {
                    SelectionKey key = keysIterator.next();
                    keysIterator.remove();

                    //NIOServer允许建立连接
                    if (key.isConnectable()) {
                        channel = (SocketChannel) key.channel();
                        if (channel.isConnectionPending()) {
                            channel.finishConnect(); //完成三次握手，建立了TCP连接
                            System.out.println("完成三次握手，封装数据后准备传输.........");
                            //封装文件数据
                            byte[] filenameBytes = filename.getBytes();

                            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize * 2 + filenameBytes.length);
                            buffer.putInt(filenameBytes.length); //先放入4个字节的int型数字，表示文件民的大小
                            buffer.put(filenameBytes); //放入文件名
                            buffer.putLong(fileSize); //long对应8个字节
                            buffer.put(file);
                            buffer.flip();

                            int sendData = channel.write(buffer);
                            System.out.println("已经发送了" + sendData + "字节的数据到" + hostname);

                            channel.register(selector, SelectionKey.OP_READ);
                        }

                    }
                    //接收到NIOServer的响应，可以读取数据
                    else if (key.isReadable()) {
                        channel = (SocketChannel) key.channel();

                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int len = channel.read(buffer);

                        if (len > 0) {
                            System.out.println("[" + Thread.currentThread().getName()
                                    + "]收到的响应" + new String(buffer.array(), 0, len));
                            sending = false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
