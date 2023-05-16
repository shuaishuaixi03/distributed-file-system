package org.wcx.dfs.namenode.server;

/**
 * 表示一条日志（对内存中文件目录树的一次操作日志）
 * @author wcx
 * @date 2023/5/14 17:44
 */
public class EditLog {
    private long txid;
    private String content;

    public EditLog(long txid, String content) {
        this.txid = txid;
        this.content = content;
    }

    public long getTxid() {
        return txid;
    }
    public void setTxid(long txid) {
        this.txid = txid;
    }
    public String getContent() {
        return content;
    }
    public void setetContent(String content) {
        this.content = content;
    }
    @Override
    public String toString() {
        return "EditLog [txid=" + txid + ",content=" + content + "]";
    }

}
