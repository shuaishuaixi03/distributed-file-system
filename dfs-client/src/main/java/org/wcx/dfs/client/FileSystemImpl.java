package org.wcx.dfs.client;

import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.wcx.dfs.namenode.rpc.model.*;
import org.wcx.dfs.namenode.rpc.service.NameNodeServiceGrpc;

/**
 * 文件系统客户端的实现类
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

    /**
     * 创建目录
     * @param path 目录对应的文件路径
     * @throws Exception
     */
    @Override
    public void mkdir(String path) throws Exception {
        MkdirRequest request = MkdirRequest.newBuilder()
                .setPath(path)
                .build();
        MkdirResponse response = namenode.mkdir(request);

        System.out.println("创建目录的响应: " + response.getStatus());
    }

    /**
     * 优雅关闭
     * @throws Exception
     */
    @Override
    public void shutdown() throws Exception {
        ShutdownRequest request = ShutdownRequest.newBuilder()
                .setCode(1)
                .build();
        this.namenode.shutdown(request);
    }

    /**
     * 上传文件
     * @param file 文件的字节数组
     * @param filename 文件名
     * @throws Exception
     */
    public Boolean upload(byte[] file, String filename) throws Exception {
        if (!createFile(filename)) {
            return false;
        }

        return true;
    }

    private Boolean createFile(String filename) {
        CreateFileRequest request = CreateFileRequest.newBuilder()
                .setFilename(filename)
                .build();
        CreateFileResponse response = namenode.create(request);

        if (response.getStatus() == 1) {
            return true;
        }
        return false;
    }
}
