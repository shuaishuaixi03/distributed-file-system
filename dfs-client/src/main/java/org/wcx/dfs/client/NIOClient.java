package org.wcx.dfs.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author wangchengxi
 * @date 2023/7/20 9:47
 */
public class NIOClient {
    public static void sendFile(byte[] file, long fileSize) {
        SocketChannel channel = null;
        Selector selector = null;
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress("localhost", 9000));
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_CONNECT);

            boolean sending = true;

            while (sending) {
                selector.select();
                Iterator<SelectionKey> keysIterator = selector.selectedKeys().iterator();

                while(keysIterator.hasNext()) {
                    SelectionKey key = keysIterator.next();
                    keysIterator.remove();

                    if (key.isConnectable()) {
                        channel = (SocketChannel) key.channel();
                        if (channel.isConnectionPending()) {
                            channel.finishConnect();

                            long imageLength = fileSize;
                            ByteBuffer buffer = ByteBuffer.allocate((int) imageLength * 2);
                            buffer.putLong(imageLength);
                            buffer.put(file);

                            channel.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            channel = (SocketChannel) key.channel();

                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int len = channel.read(buffer);

                            if (len > 0) {
                                System.out.println("[" + Thread.currentThread().getName()
                                        + "]收到的响应" + new String(buffer.array(), 0, len));
                            }
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
