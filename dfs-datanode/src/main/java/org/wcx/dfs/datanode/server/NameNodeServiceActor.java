package org.wcx.dfs.datanode.server;

import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.wcx.dfs.namenode.rpc.model.HeartbeatRequest;
import org.wcx.dfs.namenode.rpc.model.RegisterRequest;
import org.wcx.dfs.namenode.rpc.service.NameNodeServiceGrpc;

import static org.wcx.dfs.datanode.server.DataNodeConfig.*;


/**
 *
 * @author wcx
 * @date 2023/5/4 16:20
 */
public class NameNodeServiceActor {

    private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;



    public NameNodeServiceActor() {
        ManagedChannel channel = NettyChannelBuilder
                .forAddress(NAMENODE_HOSTNAME, NAMENODE_PORT)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
        this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
    }

    /**
     * 向对应的Namenode发送注册信息
     * @throws Exception
     */
    public void register() throws Exception {
        Thread registerThread = new RegisterThread();
        registerThread.start();
        registerThread.join();
    }

    /**
     * 开启向对应的Namenode发送心跳信息的线程
     */
    public void startHearbeat() {
        new HeartbeatThread().start();
    }

    class RegisterThread extends Thread {

        @Override
        public void run() {
            try {
                //TODO 发送rpc请求到NameNode进行注册，携带自己的ip和hostname
                System.out.println("发送rpc请求到namenode进行注册");

                RegisterRequest request = RegisterRequest.newBuilder()
                        .setIp(DATANODE_IP)
                        .setHostname(DATANODE_HOSTNAME)
                        .setNioPort(NIO_PORT)
                        .build();
                namenode.register(request);
//                RegisterResponse response = namenode.register(request);
//                System.out.println("接收到NameNode返回的注册响应: " + response.getStatus());
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

                    HeartbeatRequest request = HeartbeatRequest.newBuilder()
                            .setIp(DATANODE_IP)
                            .setHostname(DATANODE_HOSTNAME)
                            .setNioPort(NIO_PORT)
                            .build();
                    namenode.heartbeat(request);
//                    HeartbeatResponse response = namenode.heartbeat(request);
//                    System.out.println("接收到NameNode返回的心跳响应: " + response.getStatus());
                    //30s后再向namenode节点发送心跳信息
                    Thread.sleep(30 * 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
