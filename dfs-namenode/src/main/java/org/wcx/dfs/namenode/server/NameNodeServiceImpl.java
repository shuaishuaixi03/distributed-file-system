package org.wcx.dfs.namenode.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.grpc.stub.StreamObserver;
import org.wcx.dfs.namenode.rpc.model.*;
import org.wcx.dfs.namenode.rpc.service.NameNodeServiceGrpc;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * NameNode的rpc服务接口的实现类
 * @author wcx
 * @date 2023/5/9 15:41
 */
public class NameNodeServiceImpl implements NameNodeServiceGrpc.NameNodeService{

    public static final Integer STATUS_SUCCESS = 1;
    public static final Integer STATUS_FAILURE = 2;
    public static final Integer STATUS_SHUTDOWN = 3;

    public static final Integer BACKUP_NODE_FETCH_SIZE = 10;

    //管理元数据的组件
    private FSNamesystem namesystem;

    //管理datanode集群的组件
    private DataNodeManager datanodeManager;

    //是否正在运行
    private volatile Boolean shouldRunning = true;

    /**
     * 当前backupNode节点同步到了哪一条txid了
     */
    private long syncedTxid = 0L;

    /**
     * 当前缓冲区的一小部分editslog
     */
    private JSONArray currentBufferedEditsLog = new JSONArray();

    /**
     * 当前缓存里的editslog最大的txid
     */
    private long currentBufferedMaxTxid = 0L;

    /**
     * 当前内存里缓冲了哪个磁盘文件的数据
     */
    private String bufferedFlushedTxid;

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

    @Override
    public void mkdir(MkdirRequest request, StreamObserver<MkdirResponse> responseObserver) {
        try {
            MkdirResponse response = null;

            if (!shouldRunning) {
                response = MkdirResponse.newBuilder()
                        .setStatus(STATUS_SHUTDOWN)
                        .build();
            } else {
                this.namesystem.mkdir(request.getPath());

                response = MkdirResponse.newBuilder()
                        .setStatus(STATUS_SUCCESS)
                        .build();
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void shutdown(ShutdownRequest request, StreamObserver<ShutdownResponse> responseObserver) {
        this.shouldRunning = false;
        this.namesystem.flush();
    }

    @Override
    public void fetchEditsLog(FetchEditsLogRequest request, StreamObserver<FetchEditsLogResponse> responseObserver) {
        FetchEditsLogResponse response = null;
        JSONArray fetchedEditsLog = new JSONArray();

        List<String> flushedTxids = namesystem.getEditlog().getFlushedTxids();

        //此时没有editslog数据被刷入磁盘中，backupnode需要从currentBuffer缓冲区中拉取数据
        if (flushedTxids.size() == 0) {
            System.out.println("暂时没有任何磁盘文件，直接从内存缓冲中拉取editslog......");
            fetchFromBufferedEditsLog(fetchedEditsLog);
        }
        //此时有editslog数据被刷入磁盘中
        else {
            //backnodeup_editslog缓冲区有从磁盘文件中读取的数据
            if (bufferedFlushedTxid != null) {
                //如果backupnode拉取的editslog数据在当前的editslog磁盘文件中
                if (existInFlushedFile(bufferedFlushedTxid)) {
                    System.out.println("上一次已经缓存过磁盘文件的数据，直接从磁盘文件缓存中拉取editslog......");
                    fetchFromCurrentBuffer(fetchedEditsLog);
                }
                //如果backupnode拉取的editslog数据不在editslog磁盘文件中，需要从下一个磁盘文件中读取
                else {
                    String nextFlushedTxid = getNextFlushedTxid(flushedTxids, bufferedFlushedTxid);
                    //找到了要拉取editslog数据的磁盘editslog文件
                    if (nextFlushedTxid != null) {
                        System.out.println("上一次缓存的磁盘文件找不到要拉取的数据，从下一个磁盘文件中拉取editslog......");
                        fetchFromFlushedFile(nextFlushedTxid, fetchedEditsLog);
                    }
                    //没找到，说明要拉取的editslog数据在currentBuffer缓冲区中
                    else {
                        System.out.println("上一次缓存的磁盘文件找不到要拉取的数据，而且没有下一个磁盘文件，尝试从内存缓冲中拉取editslog......");
                        fetchFromBufferedEditsLog(fetchedEditsLog);
                    }
                }
            } else {
                Boolean fetchFromFlushedFile = false;

                //backupnode要拉取的数据在某一个磁盘文件中
                for (String flushedTxid : flushedTxids) {
                    if (existInFlushedFile(flushedTxid)) {
                        System.out.println("尝试从磁盘文件中拉取editslog, flushedTxid=" + flushedTxid);
                        fetchFromFlushedFile(flushedTxid, fetchedEditsLog);
                        fetchFromFlushedFile = true;
                        break;
                    }
                }

                //backupnode要拉取的数据比磁盘文件中的新，在currentBuffer缓冲区中
                if (!fetchFromFlushedFile) {
                    System.out.println("所有磁盘文件都没找到要拉取的editslog, 尝试直接从内存缓冲中拉取editslog......");
                    fetchFromBufferedEditsLog(fetchedEditsLog);
                }
            }
        }
        response = FetchEditsLogResponse.newBuilder()
                .setEditsLog(fetchedEditsLog.toJSONString())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 获取下一个磁盘文件的txid范围
     * @param flushedTxids
     * @param bufferedFlushedTxid
     * @return
     */
    private String getNextFlushedTxid(List<String> flushedTxids, String bufferedFlushedTxid) {
        for (int i = 0; i < flushedTxids.size(); i ++) {
            if (flushedTxids.get(i).equals(bufferedFlushedTxid)) {
                if (i + 1 < flushedTxids.size()) {
                    return flushedTxids.get(i + 1);
                }
            }
        }
        return null;
    }

    /**
     * 从磁盘中的editslog文件中读取数据到backupnode-editslog缓冲区中
     * @param flushedTxid
     * @param fetchedEditsLog
     */
    private void fetchFromFlushedFile(String flushedTxid, JSONArray fetchedEditsLog) {
        try {
            String[] flushedTxidSplited = flushedTxid.split("_");
            long startTxid = Long.valueOf(flushedTxidSplited[0]);
            long endTxid = Long.valueOf(flushedTxidSplited[1]);

            String currentEditsLogFile = "D:\\dfs\\editslog\\edits-"
                    + startTxid + "-" + endTxid + ".log";

            List<String> editsLogs = Files.readAllLines(Paths.get(currentEditsLogFile),
                    StandardCharsets.UTF_8);

            currentBufferedEditsLog.clear();

            for (String editsLog : editsLogs) {
                currentBufferedEditsLog.add(JSONObject.parseObject(editsLog));
                currentBufferedMaxTxid = JSONObject.parseObject(editsLog).getLongValue("txid");
            }
            bufferedFlushedTxid = flushedTxid;

            fetchFromCurrentBuffer(fetchedEditsLog);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从backupnode_editslog缓冲区中拉取数据
     * @param fetchedEditsLog
     */
    private void fetchFromCurrentBuffer(JSONArray fetchedEditsLog) {
        int fetchCount = 0;
        for (int i = 0; i < currentBufferedEditsLog.size(); i ++) {
            if (currentBufferedEditsLog.getJSONObject(i).getLong("txid") == syncedTxid + 1) {
                fetchedEditsLog.add(currentBufferedEditsLog.getJSONObject(i));
                syncedTxid = currentBufferedEditsLog.getJSONObject(i).getLong("txid");
                fetchCount ++;
            }
            if (fetchCount == BACKUP_NODE_FETCH_SIZE) {
                break;
            }
        }
    }

    /**
     * 从currentBuffer缓冲区中拉取editslog数据到backupnode_editslog缓冲区中
     * @param fetchedEditsLog
     */
    private void fetchFromBufferedEditsLog(JSONArray fetchedEditsLog) {

        long fetchTxid = syncedTxid + 1;
        if (fetchTxid <= currentBufferedMaxTxid) {
            System.out.println("尝试从内存缓冲拉取的时候，发现上一次内存缓存有数据可供拉取......");
            fetchFromCurrentBuffer(fetchedEditsLog);
            return;
        }


        currentBufferedEditsLog.clear();
        String[] bufferedEditsLog = namesystem.getEditlog().getBufferedEditsLog();

        if (bufferedEditsLog != null) {
            for (String editsLog : bufferedEditsLog) {
                currentBufferedEditsLog.add(JSONObject.parseObject(editsLog));
                // 我们在这里记录一下，当前内存缓存中的数据最大的一个txid是多少
                // 判断，内存缓存里的数据是否还可以读取，不要每次都重新从内存缓冲里加载
                currentBufferedMaxTxid = JSONObject.parseObject(editsLog).getLongValue("txid");
            }
            bufferedFlushedTxid = null;
            fetchFromCurrentBuffer(fetchedEditsLog);
        }

    }

    /**
     * 要拉取的editslog数据是否存在于磁盘的文件中
     * @param flushedTxid
     * @return
     */
    private Boolean existInFlushedFile(String flushedTxid) {
        String[] flushedTxidSplited = flushedTxid.split("_");

        long startTxid = Long.valueOf(flushedTxidSplited[0]);
        long endTxid = Long.valueOf(flushedTxidSplited[1]);
        long fetchTxid = syncedTxid + 1;

        if (fetchTxid >= startTxid && fetchTxid <= endTxid) {
            return true;
        }

        return false;

    }
}
