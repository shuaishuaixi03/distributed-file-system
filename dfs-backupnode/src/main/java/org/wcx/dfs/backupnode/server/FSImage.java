package org.wcx.dfs.backupnode.server;

/**
 * @author wcx
 * @date 2023/6/4 21:02
 */
public class FSImage {
    private long maxTxid;
    private String fsiamgeJson;
    public FSImage(long maxTxid, String fsiamgeJson) {
        this.maxTxid = maxTxid;
        this.fsiamgeJson = fsiamgeJson;
    }

    public long getMaxTxid() {
        return maxTxid;
    }

    public void setMaxTxid(long maxTxid) {
        this.maxTxid = maxTxid;
    }

    public String getFsiamgeJson() {
        return fsiamgeJson;
    }

    public void setFsiamgeJson(String fsiamgeJson) {
        this.fsiamgeJson = fsiamgeJson;
    }
}
