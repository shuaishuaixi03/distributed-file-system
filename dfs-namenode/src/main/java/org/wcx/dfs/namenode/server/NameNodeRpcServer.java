package org.wcx.dfs.namenode.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.wcx.dfs.namenode.rpc.service.NameNodeServiceGrpc;

import java.io.IOException;

/**
 * @author wcx
 * @date 2023/5/4 17:34
 */
public class NameNodeRpcServer {

    private static final int DEFAULT_PORT = 50070;

    private Server server = null;

    private FSNamesystem namesystem;

    private DataNodeManager datanodeManager;

    public NameNodeRpcServer(
            FSNamesystem namesystem,
            DataNodeManager datanodeManager) {
        this.namesystem = namesystem;
        this.datanodeManager = datanodeManager;
    }

    public void start() throws IOException {
        server = ServerBuilder
                .forPort(DEFAULT_PORT)
                .addService(NameNodeServiceGrpc.bindService(new NameNodeServiceImpl(namesystem, datanodeManager)))
                .build()
                .start();
        System.out.println("NameNodeRpcServer启动, 监听端口号: " + DEFAULT_PORT);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                NameNodeRpcServer.this.stop();
            }
        });
    }
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUnitShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}
