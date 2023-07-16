package org.wcx.dfs.client;


public class FileSystemTest {
    public static void main(String[] args) throws Exception {
        FileSystem filesystem = new FileSystemImpl();

//        for (int i = 0; i < 5; i ++) {
//            filesystem.mkdir("/usr/warehouse/hive");
//        }

//        for (int j = 0; j < 10; j ++) {
//            Thread thread = new Thread() {
//                @Override
//                public void run() {
//                    for (int i = 0; i < 100; i ++) {
//                        try {
//                            filesystem.mkdir("/usr/warehouse/hive2" + i + "_" + Thread.currentThread().getName());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            };
//            thread.start();
//            thread.join();
//        }
        filesystem.shutdown();
    }
}