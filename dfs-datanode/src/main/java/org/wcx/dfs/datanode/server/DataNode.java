package org.wcx.dfs.datanode.server;

import java.io.File;

/**
 * @author wcx
 * @date 2023/5/4 16:12
*/
public class DataNode {
    //表示datanode节点是否正在运行
    private volatile Boolean shouldRun;
    private NameNodeRpcClient nameNodeRpcClient;

    /**
     * 心跳管理组件
     */
    private HeartbeatManager heartbeatManager;

    /**
     * 磁盘存储管理组件
     */
    private StorageManager storageManager;

    /**
     * 初始化DataNode
     * @throws Exception
     */
    public DataNode() throws Exception {
        this.shouldRun = true;
        this.nameNodeRpcClient = new NameNodeRpcClient();
        Boolean registerResult = this.nameNodeRpcClient.register();




        StorageInfo storageInfo = getStorageInfo();
        if (storageInfo != null) {
            this.nameNodeRpcClient.reportCompleteStorageInfo(storageInfo);
        }

        DataNodeNIOServer nioServer = new DataNodeNIOServer();
        nioServer.start();
    }




    /**
     * 启动DataNode
     */
    private void start() {
        try {
            while (shouldRun) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        DataNode datanode = new DataNode();
        datanode.start();
    }

}
