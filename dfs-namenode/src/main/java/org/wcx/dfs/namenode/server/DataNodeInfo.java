package org.wcx.dfs.namenode.server;

/**
 * @author wcx
 * @date 2023/5/4 17:12
 */
public class DataNodeInfo  {
    private String ip;
    private String hostname;

    private long latestHeartbeatTime;

    public DataNodeInfo(String ip, String hostname) {
        this.ip = ip;
        this.hostname = hostname;
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

    public long getLatestHeartbeatTime() {
        return latestHeartbeatTime;
    }

    public void setLatestHeartbeatTime(long latestHeartbeatTime) {
        this.latestHeartbeatTime = latestHeartbeatTime;
    }
}
