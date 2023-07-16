package org.wcx.dfs.backupnode.server;

import com.alibaba.fastjson.JSONArray;
import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.wcx.dfs.namenode.rpc.model.FetchEditsLogRequest;
import org.wcx.dfs.namenode.rpc.model.FetchEditsLogResponse;
import org.wcx.dfs.namenode.rpc.model.UpdateCheckpointTxidRequest;
import org.wcx.dfs.namenode.rpc.service.NameNodeServiceGrpc;

/**
 * @author wcx
 * @date 2023/5/19 17:17
 */
public class NameNodeRpcClient {

    private static final String NAMENODE_HOSTNAME = "localhost";

    private static final Integer NAMENODE_PORT = 50070;

    //backupnode判断namenode是否正常运行
    private Boolean shouldNamenodeRunninng = true;

    private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;

    public NameNodeRpcClient() {
        ManagedChannel channel = NettyChannelBuilder
                .forAddress(NAMENODE_HOSTNAME, NAMENODE_PORT)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
        this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
    }

    /**
     * 获取editslog数据
     * @return
     */
    public JSONArray fetchEditsLog(long syncedTxid) {
        FetchEditsLogRequest request = FetchEditsLogRequest.newBuilder()
                .setSyncedTxid(syncedTxid)
                .build();

        FetchEditsLogResponse response = namenode.fetchEditsLog(request);
        String editsLogJson = response.getEditsLog();

        return JSONArray.parseArray(editsLogJson);
    }

    /**
     * 更新checkpoint txid
     * @param txid
     */
    public void updateCheckpointTxid(long txid) {
        UpdateCheckpointTxidRequest request = UpdateCheckpointTxidRequest.newBuilder()
                .setTxid(txid)
                .build();
        namenode.updateCheckpointTxid(request);
    }

    public Boolean getShouldNamenodeRunninng() {
        return this.shouldNamenodeRunninng;
    }
    public void setShouldNamenodeRunninng(Boolean shouldNamenodeRunninng) {
        this.shouldNamenodeRunninng = shouldNamenodeRunninng;
    }
}
