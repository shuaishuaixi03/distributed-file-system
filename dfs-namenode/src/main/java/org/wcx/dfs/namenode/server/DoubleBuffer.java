package org.wcx.dfs.namenode.server;

/**
 * 用来操作内存双缓冲区的组件
 * @author wcx
 * @date 2023/5/14 17:41
 */
public class DoubleBuffer {
    public static final Long EDIT_LOG_BUFFER_LIMIT = 512 * 1024L;

    //currentBuffer缓冲区用来写入文件目录树的操作日志
    private EditLogBuffer currentBuffer = new EditLogBuffer();

    //syncBuffer缓冲区用来刷盘
    private EditLogBuffer syncBuffer = new EditLogBuffer();


    public void write(EditLog log) {
        currentBuffer.write(log);
    }

    //判断currentBuffer缓存区是否写满，需要刷入到磁盘中
    public boolean shouldSyncToDisk() {
        if(currentBuffer.size() >= EDIT_LOG_BUFFER_LIMIT) {
            return true;
        }
        return false;
    }

    //交换缓冲区
    public void setReadyToSync() {
        EditLogBuffer temp = currentBuffer;
        currentBuffer = syncBuffer;
        syncBuffer = temp;
    }

    //syncBuffer缓冲区刷磁盘操作
    public void flush() {
        syncBuffer.flush();
        syncBuffer.clear();
    }

    /**
     * 表示缓冲区
     */
    class EditLogBuffer {
        public void write(EditLog log) {

        }
        public Long size() {
            return 0L;
        }
        public void flush() {

        }
        public void clear() {

        }
    }
}
