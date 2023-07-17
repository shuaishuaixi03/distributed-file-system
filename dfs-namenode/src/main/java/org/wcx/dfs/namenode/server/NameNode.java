package org.wcx.dfs.namenode.server;


/**
 * NameNode核心启动类
 * @author wcx
 * @date 2023/5/4 17:17
 */
public class NameNode {

    /**
     * 负责管理元数据的核心组件: 管理文件目录树
     */
    private FSNamesystem namesystem;

    /**
     * 负责管理集群中所有的Datanode的组件
     */
    private DataNodeManager datanodeManager;

    /**
     * Namenode对外提供rpc接口的server，可以响应请求
     */
    private NameNodeRpcServer rpcServer;

    /**
     * 接受backupnode上传的fsimage文件的server
     */
    private FSImageUploadServer fsImageUploadServer;

    /**
     * 初始化Namenode
     * @throws Exception
     */
    private void initialize() throws Exception {
        this.namesystem = new FSNamesystem();
        this.datanodeManager = new DataNodeManager();
        this.rpcServer = new NameNodeRpcServer(this.namesystem, this.datanodeManager);
        this.fsImageUploadServer = new FSImageUploadServer();
    }

    /**
     * 让Namenode运行起来
     */
    private void start() throws Exception {
        this.fsImageUploadServer.start();
        this.rpcServer.start();
        this.rpcServer.blockUntilShutdown();
    }

    public static void main(String[] args) throws Exception {
        NameNode namenode = new NameNode();
        namenode.initialize();
        namenode.start();
    }

}
