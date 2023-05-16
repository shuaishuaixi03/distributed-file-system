package org.wcx.dfs.namenode.server;

import io.grpc.stub.StreamObserver;
import org.wcx.dfs.namenode.rpc.model.HeartbeatRequest;
import org.wcx.dfs.namenode.rpc.model.HeartbeatResponse;
import org.wcx.dfs.namenode.rpc.model.RegisterRequest;
import org.wcx.dfs.namenode.rpc.model.RegisterResponse;
import org.wcx.dfs.namenode.rpc.service.NameNodeServiceGrpc;

/**
 * NameNode的rpc服务接口的实现类
 * @author wcx
 * @date 2023/5/9 15:41
 */
public class NameNodeServiceImpl implements NameNodeServiceGrpc.NameNodeService{

    public static final Integer STATUS_SUCCESS = 1;
    public static final Integer STATUS_FAILURE = 2;

    //管理元数据的组件
    private FSNamesystem namesystem;

    //管理datanode集群的组件
    private DataNodeManager datanodeManager;

    public NameNodeServiceImpl(
            FSNamesystem namesystem,
            DataNodeManager datanodeManager
            ) {
        this.namesystem = namesystem;
        this.datanodeManager = datanodeManager;
    }



    @Override
    public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
        datanodeManager.register(request.getIp(), request.getHostname());

        RegisterResponse response = RegisterResponse.newBuilder()
                .setStatus(STATUS_SUCCESS)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void heartbeat(HeartbeatRequest request, StreamObserver<HeartbeatResponse> responseObserver) {
        datanodeManager.heartbeat(request.getIp(), request.getHostname());

        HeartbeatResponse response = HeartbeatResponse.newBuilder()
                .setStatus(STATUS_SUCCESS)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
