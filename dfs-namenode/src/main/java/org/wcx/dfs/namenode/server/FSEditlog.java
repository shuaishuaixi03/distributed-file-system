package org.wcx.dfs.namenode.server;


import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 负责管理edits log日志
 * @author wcx
 * @date 2023/5/4 17:26
 */
public class FSEditlog {

    /**
     * editlog日志文件清理的时间间隔
     */
    private static final Long EDIT_LOG_CLEAN_INTERVAL = 30 * 1000L;

    /**
     * 元数据管理组件
     */
    private FSNamesystem namesystem;

    //当前递增到的日志序列号
    private long txidSeq = 0L;
    //内存双缓存区
    private DoubleBuffer doubleBuffer = new DoubleBuffer();
    //是否正在刷磁盘
    private volatile Boolean shouldSyncRunning = false;
    //同步到磁盘中的最大日志序列号
    private volatile Long syncTxid = 0L;
    //是否正在调度一次刷盘的操作
    private volatile Boolean shouldSchedulingSync = false;
    //
    private ThreadLocal<Long> localTxid = new ThreadLocal<>();


    public FSEditlog(FSNamesystem namesystem) {
        this.namesystem = namesystem;

        EditLogCleaner editLogCleaner = new EditLogCleaner();
        editLogCleaner.start();
    }

    public void logEdit(String content) {
        synchronized(this) {
            //
            waitSchedulingSync();

            txidSeq ++;
            long txid = txidSeq;
            localTxid.set(txid);

            EditLog log = new EditLog(txid, content);

            try {
                doubleBuffer.write(log);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
                if(txid <= syncTxid) {
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
            doubleBuffer.setReadyToSync();
            syncTxid = txid;
            shouldSchedulingSync = false;
            notifyAll();
            shouldSyncRunning = true;
        }
        try {
            doubleBuffer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        synchronized(this) {
            shouldSyncRunning = false;
            notifyAll();
        }
    }

    //强制把内存缓冲区中的数据刷入磁盘中
    public void flush() {
        try {
            doubleBuffer.setReadyToSync();
            doubleBuffer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取已经刷入磁盘的editslog数据
     * @return
     */
    public List<String> getFlushedTxids() {
        synchronized(this) {
            return doubleBuffer.getFlushedTxids();
        }
    }

    /**
     * 获取当前缓冲区的editslog数据
     * @return
     */
    public String[] getBufferedEditsLog() {
        synchronized(this) {
            return doubleBuffer.getBufferedEditsLog();
        }
    }

    class EditLogCleaner extends Thread {
        @Override
        public void run() {
            System.out.println("editlog日志文件后台清理线程启动......");

            while (true) {
                try {
                    Thread.sleep(EDIT_LOG_CLEAN_INTERVAL);

                    List<String> flushedTxids = getFlushedTxids();
                    if (flushedTxids != null && flushedTxids.size() > 0) {
                        long checkpointTxid = namesystem.getCheckpointTxid();

                        for (String flushedTxid : flushedTxids) {
                            long startTxid = Long.valueOf(flushedTxid.split("_")[0]);
                            long endTxid = Long.valueOf(flushedTxid.split("_")[1]);

                            if (checkpointTxid >= endTxid) {
                                File file = new File("D:\\dfs\\editslog\\edits-"
                                        + startTxid + "-" + endTxid + ".log");

                                if (file.exists()) {
                                    file.delete();
                                    System.out.println("发现editlog日志文件不是必要，进行删除操作。文件路径为: " + file.getPath());
                                }
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
