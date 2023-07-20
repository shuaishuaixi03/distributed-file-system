package org.wcx.dfs.namenode.server;

/**
 * @author wcx
 * @date 2023/5/4 17:12
 */
public class DataNodeInfo implements Comparable<DataNodeInfo> {
    /**
     * ip地址
     */
    private String ip;
    /**
     * 机器名字
     */
    private String hostname;

    /**
     * NIO端口号
     */
    private int nioPort;

    /**
     * 上一次心跳时间
     */
    private long latestHeartbeatTime;

    /**
     * 已经存储数据的大小
     */
    private long storedDataSize;

    public DataNodeInfo(String ip, String hostname, int nioPort) {
        this.ip = ip;
        this.hostname = hostname;
        this.nioPort = nioPort;
        this.latestHeartbeatTime = System.currentTimeMillis();
        this.storedDataSize = 0L;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getNioPort() {
        return nioPort;
    }

    public void setNioPort(int nioPort) {
        this.nioPort = nioPort;
    }

    public long getLatestHeartbeatTime() {
        return latestHeartbeatTime;
    }

    public void setLatestHeartbeatTime(long latestHeartbeatTime) {
        this.latestHeartbeatTime = latestHeartbeatTime;
    }

    public long getStoredDataSize() {
        return storedDataSize;
    }

    public void setStoredDataSize(long storedDataSize) {
        this.storedDataSize = storedDataSize;
    }

    public void addStoredDataSize(long storedDataSize) {
        this.storedDataSize += storedDataSize;
    }


    @Override
    public String toString() {
        return "DataNodeInfo{" +
                "ip='" + ip + '\'' +
                ", hostname='" + hostname + '\'' +
                ", latestHeartbeatTime=" + latestHeartbeatTime +
                ", storedDataSize=" + storedDataSize +
                '}';
    }

    @Override
    public int compareTo(DataNodeInfo o) {
        if (this.storedDataSize - o.getStoredDataSize() > 0) {
            return 1;
        } else if (this.storedDataSize - o.getStoredDataSize() < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
