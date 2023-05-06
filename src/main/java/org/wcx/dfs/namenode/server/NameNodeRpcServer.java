package org.wcx.dfs.namenode.server;

import java.util.HashMap;

/**
 * @author wcx
 * @date 2023/5/4 17:34
 */
public class NameNodeRpcServer {

    private FSNamesystem namesystem;

    private DataNodeManager datanodeManager;

    public NameNodeRpcServer(
            FSNamesystem namesystem,
            DataNodeManager datanodeManager
    ) {
        this.namesystem = namesystem;
    }

    public Boolean mkdir(String path) throws Exception{
        return this.namesystem.mkdir(path);
    }

    public Boolean register(String ip, String hostname) throws Exception{
        return datanodeManager.register(ip, hostname);
    }

    public Boolean heartbeat(String ip, String hostname) throws Exception {
        return datanodeManager.heartbeat(ip, hostname);
    }
    public void start() {
        System.out.println("rpc server启动......");
    }

}
