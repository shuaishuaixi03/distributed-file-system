package org.wcx.dfs.datanode.server;

import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.wcx.dfs.namenode.rpc.model.HeartbeatRequest;
import org.wcx.dfs.namenode.rpc.model.InformReplicaReceivedRequest;
import org.wcx.dfs.namenode.rpc.model.RegisterRequest;
import org.wcx.dfs.namenode.rpc.service.NameNodeServiceGrpc;

import static org.wcx.dfs.datanode.server.DataNodeConfig.*;


/**
 * 负责和Namenode通信的组件
 * @author wcx
 * @date 2023/5/4 16:20
 */
public class NameNodeRpcClient {

    private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;



    public NameNodeRpcClient() {
        ManagedChannel channel = NettyChannelBuilder
                .forAddress(NAMENODE_HOSTNAME, NAMENODE_PORT)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
        this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
        System.out.println("跟NameNode的" + NAMENODE_PORT + "端口建立连接......");
    }

    /**
     * 启动组件
     * @throws Exception
     */
    public void start() throws Exception {
        register();
        startHearbeat();
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
     * 开启执行定时向对应的Namenode发送心跳信息的线程
     */
    public void startHearbeat() {
        new HeartbeatThread().start();
    }

    /**
     * 通知Master节点自己收到了一个文件的副本
     * @param filename
     * @throws Exception
     */
    public void informReplicaReceived(String filename) throws Exception {
        InformReplicaReceivedRequest request = InformReplicaReceivedRequest.newBuilder()
                .setFilename(filename)
                .build();
        namenode.informReplicaReceived(request);
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
                System.out.println("完成向NameNode的注册......");
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
