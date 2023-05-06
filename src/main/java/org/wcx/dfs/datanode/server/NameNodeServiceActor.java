package org.wcx.dfs.datanode.server;

import java.util.concurrent.CountDownLatch;

/**
 * @author wcx
 * @date 2023/5/4 16:20
 */
public class NameNodeServiceActor {

    public void register(CountDownLatch latch) {
        Thread registerThread = new RegisterThread(latch);
        registerThread.start();
    }

    public void startHearbeat() {
        new HeartbeatThread().start();
    }

    class RegisterThread extends Thread {
        CountDownLatch latch;
        public RegisterThread(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                //TODO 发送rpc请求到NameNode进行注册，携带自己的ip和hostname
                System.out.println("发送rpc请求到namenode节点上进行注册");
                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class HeartbeatThread extends Thread {
        @Override
        public void run() {
            try {
                while(true) {
                    System.out.println("发送rpc请求到namenode节点上进行心跳");

                    //30s后再向namenode节点发送心跳信息
                    Thread.sleep(30 * 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
