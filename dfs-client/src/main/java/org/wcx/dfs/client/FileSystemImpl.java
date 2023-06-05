package org.wcx.dfs.client;

import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.wcx.dfs.namenode.rpc.model.MkdirRequest;
import org.wcx.dfs.namenode.rpc.model.MkdirResponse;
import org.wcx.dfs.namenode.rpc.model.ShutdownRequest;
import org.wcx.dfs.namenode.rpc.service.NameNodeServiceGrpc;

/**
 * @author wcx
 * @date 2023/5/17 19:15
 */
public class FileSystemImpl implements FileSystem{

    private static final String NAMENODE_HOSTNAME = "localhost";
    private static final Integer NAMENODE_PORT = 50070;

    private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;

    public FileSystemImpl() {
        ManagedChannel channel = NettyChannelBuilder
                .forAddress(NAMENODE_HOSTNAME, NAMENODE_PORT)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
        this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void mkdir(String path) throws Exception {
        MkdirRequest request = MkdirRequest.newBuilder()
                .setPath(path)
                .build();
        MkdirResponse response = namenode.mkdir(request);

        System.out.println("创建目录的响应: " + response.getStatus());
    }

    @Override
    public void shutdown() throws Exception {
        ShutdownRequest request = ShutdownRequest.newBuilder()
                .setCode(1)
                .build();
        this.namenode.shutdown(request);
    }
}
