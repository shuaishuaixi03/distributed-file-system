package org.wcx.dfs.namenode.server;


import com.alibaba.fastjson.JSONObject;

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

        JSONObject jsonObject = JSONObject.parseObject(content);
        jsonObject.put("txid", txid);
        this.content = jsonObject.toJSONString();
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
    public void setContent(String content) {
        this.content = content;
    }
    @Override
    public String toString() {
        return "EditLog [txid=" + txid + ",content=" + content + "]";
    }

}
