package org.wcx.dfs.namenode.server;

import java.io.IOException;

/**
 * NameNode核心启动类
 * @author wcx
 * @date 2023/5/4 17:17
 */
public class NameNode {
    //NameNode节点是否正常运行
    private volatile Boolean shouldRun;

    private FSNamesystem namesystem;

    //负责管理DataNode集群的组件
    private DataNodeManager datanodeManager;

    private NameNodeRpcServer rpcServer;

    public NameNode() {
        this.shouldRun = true;
    }

    private void initialize() throws Exception {
        this.namesystem = new FSNamesystem();
        this.datanodeManager = new DataNodeManager();
        this.rpcServer = new NameNodeRpcServer(this.namesystem, this.datanodeManager);
        this.rpcServer.start();
    }

    private void run() {
        try {
            while (shouldRun) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        NameNode namenode = new NameNode();
        namenode.initialize();
        namenode.run();
    }

}
