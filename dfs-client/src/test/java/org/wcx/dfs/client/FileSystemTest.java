package org.wcx.dfs.client;


import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileSystemTest {

    private static FileSystem fileSystem = new FileSystemImpl();
    public static void main(String[] args) throws Exception {
//        testShutdown();
        testCreateFile();
    }

    private static void testMkdir() throws Exception {
        for (int j = 0; j < 10; j ++) {
            new Thread() {
                public void run() {
                    for (int i = 0; i < 100; i ++) {
                        try {
                            fileSystem.mkdir("/usr/warehouse/spark" + i + "_" + Thread.currentThread().getName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }

    private static void testShutdown() throws Exception {
        fileSystem.shutdown();
    }
    private static void testCreateFile() throws Exception {
        File image = new File("D:\\dfs\\tmp\\timg.jpg");
        long imageLength = image.length();

        ByteBuffer buffer = ByteBuffer.allocate((int) imageLength);

        FileInputStream imageIn = new FileInputStream(image);
        FileChannel imageChannel = imageIn.getChannel();
        imageChannel.read(buffer);

        buffer.flip();
        byte[] imageBytes = buffer.array();

        System.out.println("成功读取大小为: " + imageLength + "的文件，开始上传文件");
        fileSystem.upload(imageBytes, "/image/product/iphone.jpg", imageLength);

        imageIn.close();
        imageChannel.close();

    }
}