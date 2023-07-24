package org.wcx.dfs.namenode.server;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 负责管理集群中所有datanode
 * @author wcx
 * @date 2023/5/4 17:14
 */
public class DataNodeManager {

    /**
     * 集群中所有datanode信息
     */
    private Map<String, DataNodeInfo> datanodes = new ConcurrentHashMap<>();

    public DataNodeManager() {
        new DataNodeAliveMonitor().start();
    }


    /**
     * Datanode进行注册
     * @param ip
     * @param hostname
     * @return
     */
    public Boolean register(String ip, String hostname, int nioPort) {
        DataNodeInfo datanode = new DataNodeInfo(ip, hostname, nioPort);
        datanodes.put(ip + "-" + hostname, datanode);
        System.out.println("DataNode注册: ip=" + ip + ",hostname=" + hostname + ",nioPort=" + nioPort);
        return true;
    }

    /**
     * Datanode进行心跳
     * @param ip
     * @param hostname
     * @return
     */
    public Boolean heartbeat(String ip, String hostname) {
        DataNodeInfo datanode = datanodes.get(ip + "-" + hostname);
        datanode.setLatestHeartbeatTime(System.currentTimeMillis());
        System.out.println("DataNode发送心跳: ip=" + ip + ",hostname=" + hostname);
        return true;
    }

    /**
     * 给要上传的文件分配对应的两个Datnode
     * @param fileSize
     * @return
     */
    public List<DataNodeInfo> allocateDataNodes(long fileSize) {
        synchronized(this) {
            List<DataNodeInfo> datanodeList = new ArrayList<>();
            for (DataNodeInfo datanode : datanodes.values()) {
                datanodeList.add(datanode);
            }

            Collections.sort(datanodeList);


            List<DataNodeInfo> selectedDatanodes = new ArrayList<>();
            if (datanodeList.size() >= 2) {
                selectedDatanodes.add(datanodeList.get(0));
                selectedDatanodes.add(datanodeList.get(1));

                datanodeList.get(0).addStoredDataSize(fileSize);
                datanodeList.get(1).addStoredDataSize(fileSize);
            }

            return datanodeList;

        }
    }

    /**
     * 设置一个数据节点的存储数据大小
     * @param ip
     * @param hostname
     * @param storedDataSize
     */
    public void setStoredDataSize(String ip, String hostname, long storedDataSize) {
        DataNodeInfo datanode = datanodes.get(ip + "-" + hostname);
        datanode.setStoredDataSize(storedDataSize);
    }

    /**
     * 获取DataNode信息
     * @param ip
     * @param hostname
     * @return
     */
    public DataNodeInfo getDatanode(String ip, String hostname) {
        return datanodes.get(ip + "-" + hostname);
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
