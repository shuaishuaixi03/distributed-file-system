package org.wcx.dfs.namenode.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wcx
 * @date 2023/5/4 17:14
 */
public class DataNodeManager {

    //NameNode节点记录datanode信息的列表
    private Map<String, DataNodeInfo> datanodes = new ConcurrentHashMap<>();

    public DataNodeManager() {
        new DataNodeAliveMonitor().start();
    }

    public Boolean register(String ip, String hostname) {
        DataNodeInfo datanode = new DataNodeInfo(ip, hostname);
        datanodes.put(ip + "-" + hostname, datanode);
        System.out.println("DataNode注册: ip=" + ip + ",hostname=" + hostname);
        return true;
    }

    public Boolean heartbeat(String ip, String hostname) {
        DataNodeInfo datanode = datanodes.get(ip + "-" + hostname);
        datanode.setLatestHeartbeatTime(System.currentTimeMillis());
        System.out.println("DataNode发送心跳: ip=" + ip + ",hostname=" + hostname);
        return true;
    }

    class DataNodeAliveMonitor extends Thread {
        @Override
        public void run() {
            try {
                while(true) {
                    List<String> toRemoveDatanodes = new ArrayList<>();

                    Iterator<DataNodeInfo> datanodesIterator = datanodes.values().iterator();
                    DataNodeInfo datanode = null;
                    while (datanodesIterator.hasNext()) {
                        datanode = datanodesIterator.next();
                        //将已经超过90s没有收到心跳信息的datanode节点加入待移除队列
                        if (System.currentTimeMillis() - datanode.getLatestHeartbeatTime() > 90 * 1000) {
                            toRemoveDatanodes.add(datanode.getIp() + "-" + datanode.getHostname());
                        }
                    }

                    if (!toRemoveDatanodes.isEmpty()) {
                        for (String toRemoveDatanode : toRemoveDatanodes) {
                            datanodes.remove(toRemoveDatanode);
                        }
                    }
                    //每隔30s检测一次
                    Thread.sleep(30 * 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
