package org.wcx.dfs.namenode.server;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用来操作内存双缓冲区的组件
 * @author wcx
 * @date 2023/5/14 17:41
 */
public class DoubleBuffer {
    //一块editslog缓冲区的最大容量
    public static final Integer EDIT_LOG_BUFFER_LIMIT = 25 * 1024;

    //专门用来承载线程写入editslog日志
    private EditLogBuffer currentBuffer = new EditLogBuffer();

    //专门用来将editslog日志数据刷入到磁盘中
    private EditLogBuffer syncBuffer = new EditLogBuffer();

    //写入editslog日志中最大的一个txid
    private Long startTxid = 1L;

    //已经刷入到磁盘中editslog日志的txid范围
    private List<String> flushedTxids = new CopyOnWriteArrayList<>();

    /**
     * 将edits log日志写入内存的currentBuffer缓冲区中
     * @param log
     * @throws IOException
     */
    public void write(EditLog log) throws IOException {
        currentBuffer.write(log);
    }

    /**
     * 判断内存的currentBuffer缓冲区是否写满，是否需要刷盘操作
     */
    public boolean shouldSyncToDisk() {
        if(currentBuffer.size() >= EDIT_LOG_BUFFER_LIMIT) {
            return true;
        }
        return false;
    }

    /**
     * 交换currentBuffer和syncBuffer缓冲区，为后续刷盘操作做准备工作
     */
    public void setReadyToSync() {
        EditLogBuffer temp = currentBuffer;
        currentBuffer = syncBuffer;
        syncBuffer = temp;
    }

    /**
     * 将syncBuffer缓冲区中的数据刷入磁盘中
     * @throws IOException
     */
    public void flush() throws IOException {
        syncBuffer.flush();
        syncBuffer.clear();
    }

    /**
     * 获取已经刷入磁盘的editslog日志数据的txid范围
     * @return
     */
    public List<String> getFlushedTxids() {
        return flushedTxids;
    }

    /**
     * 获取内存中currentBuffer缓冲区的editslog日志数据
     * @return
     */
    public String[] getBufferedEditsLog() {
        if (currentBuffer.size() == 0) {
            return null;
        }
        String editsLogRawData = new String(currentBuffer.getBufferData());
        return editsLogRawData.split("\n");
    }


    /**
     * 表示内存中用来存放edits log日志的缓冲区
     */
    class EditLogBuffer {

        //针对内存缓冲区的字节数组输出流
        ByteArrayOutputStream buffer;

        //上一次刷入
        long endTxid = 0L;

        public EditLogBuffer() {
            this.buffer = new ByteArrayOutputStream(EDIT_LOG_BUFFER_LIMIT * 2);
        }
        public void write(EditLog log) throws IOException {
            this.endTxid = log.getTxid();
            buffer.write(log.getContent().getBytes());
            buffer.write("\n".getBytes());
            System.out.println("写入一条editslog: " + log.getContent()
                    + ", 当前缓冲区的大小是: " + size());
        }
        public Integer size() {
            return buffer.size();
        }

        /**
         * 将sync buufer中的editslog数据刷入磁盘中
         * @throws IOException
         */
        public void flush() throws IOException {
            byte[] data = buffer.toByteArray();
            ByteBuffer dataBuffer = ByteBuffer.wrap(data);

            String editsLogFilePath = "D:\\dfs\\editslog\\edits-" +
                    + startTxid + "-" + endTxid + ".log";
            flushedTxids.add(startTxid + "_" + endTxid);

            RandomAccessFile file = null;
            FileOutputStream out = null;
            FileChannel editsLogFileChannel = null;

            try {
                file = new RandomAccessFile(editsLogFilePath, "rw"); //读写模式, 数据写入缓冲区中
                out = new FileOutputStream(file.getFD());
                editsLogFileChannel = out.getChannel();

                editsLogFileChannel.write(dataBuffer);
                editsLogFileChannel.force(false); // 强制把数据刷入磁盘上
            } finally {
                if (out != null) {
                    out.close();
                }
                if (file != null) {
                    file.close();
                }
                if (editsLogFileChannel != null) {
                    editsLogFileChannel.close();
                }
            }

            startTxid = endTxid + 1;
        }

        /**
         * 情况当前缓冲区里的数据
         */
        public void clear() {
            buffer.reset();
        }

        /**
         * 获取当前缓冲区里的数据
         * @return
         */
        public byte[] getBufferData() {
            return buffer.toByteArray();
        }
    }
}
