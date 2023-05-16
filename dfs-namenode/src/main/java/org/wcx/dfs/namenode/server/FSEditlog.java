package org.wcx.dfs.namenode.server;


/**
 * 负责管理edits log日志
 * @author wcx
 * @date 2023/5/4 17:26
 */
public class FSEditlog {
    //当前递增到的日志序列号
    private long txidSeq = 0L;
    //内存双缓存区
    private DoubleBuffer doubleBuffer = new DoubleBuffer();
    //是否正在刷磁盘
    private volatile Boolean shouldSyncRunning = false;
    //同步到磁盘中的最大日志序列号
    private volatile Long syncMaxTxid = 0L;
    //是否正在调度一次刷盘的操作
    private volatile Boolean shouldSchedulingSync = false;
    //
    private ThreadLocal<Long> localTxid = new ThreadLocal<>();

    public void logEdit(String content) {
        synchronized(this) {
            //
            waitSchedulingSync();

            txidSeq ++;
            long txid = txidSeq;
            localTxid.set(txid);

            EditLog log = new EditLog(txid, content);

            doubleBuffer.write(log);

            if(!doubleBuffer.shouldSyncToDisk()) {
                return;
            }

            shouldSchedulingSync = true;

            logSync();
        }
    }

    //等待正在调度的刷磁盘操作
    private void waitSchedulingSync() {
        try {
            while(shouldSchedulingSync) {
                wait(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logSync() {
        synchronized(this) {
            long txid = localTxid.get();

            if(shouldSyncRunning) {
                if(txid <= syncMaxTxid) {
                    return;
                }
                try {
                    while(shouldSyncRunning) {
                        wait(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
