package org.wcx.dfs.namenode.server;

/**
 * @author wcx
 * @date 2023/5/4 17:26
 */
public class FSEditlog {
    private long txidSeq = 0L;

//    private DoubleBuffer editLogBuffer = new DoubleBuffer();

    private volatile Boolean isSyncRunning = false;

    private volatile Boolean isWaitSync = false;

    private volatile Long syncMaxTxid = 0L;


}
