package org.wcx.dfs.datanode.server;

import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.wcx.dfs.namenode.rpc.model.HeartbeatRequest;
import org.wcx.dfs.namenode.rpc.model.HeartbeatResponse;
import org.wcx.dfs.namenode.rpc.model.RegisterRequest;
import org.wcx.dfs.namenode.rpc.model.RegisterResponse;
import org.wcx.dfs.namenode.rpc.service.NameNodeServiceGrpc;


/**
 * @author wcx
 * @date 2023/5/4 16:20
 */
public class NameNodeServiceActor {

    private static final String NAMENODE_HOSTNAME = "localhost";
    private static final Integer NAMENODE_PORT = 50070;

    private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;


    public NameNodeServiceActor() {
        ManagedChannel channel = NettyChannelBuilder
                .forAddress(NAMENODE_HOSTNAME, NAMENODE_PORT)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
        this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
    }

    public void register() throws Exception {
        Thread registerThread = new RegisterThread();
        registerThread.start();
        registerThread.join();
    }

    public void startHearbeat() {
        new HeartbeatThread().start();
    }

    class RegisterThread extends Thread {

        @Override
        public void run() {
            try {
                //TODO 发送rpc请求到NameNode进行注册，携带自己的ip和hostname
                System.out.println("发送rpc请求到namenode进行注册");
                String ip = "127.0.0.1";
                String hostname = "dfs-data-01";

                RegisterRequest request = RegisterRequest.newBuilder()
                        .setIp(ip)
                        .setHostname(hostname)
                        .build();
                RegisterResponse response = namenode.register(request);
                System.out.println("接收到NameNode返回的注册响应: " + response.getStatus());
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

                    String ip = "127.0.0.1";
                    String hostname = "dfs-data-01";

                    HeartbeatRequest request = HeartbeatRequest.newBuilder()
                            .setIp(ip)
                            .setHostname(hostname)
                            .build();
                    HeartbeatResponse response = namenode.heartbeat(request);
                    System.out.println("接收到NameNode返回的心跳响应: " + response.getStatus());
                    //30s后再向namenode节点发送心跳信息
                    Thread.sleep(30 * 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
