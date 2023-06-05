package org.wcx.dfs.backupnode.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * fsimage文件的checkpoint组件
 * @author wcx
 * @date 2023/6/4 20:29
 */
public class FSImageCheckpointer extends Thread{
    /**
     * checkpoint操作的时间间隔
     */
    public static final Integer CHECKPOINT_INTERVAL = 2 * 60 * 1000;

    private BackupNode backupNode;
    private FSNamesystem namesystem;

    private String lastFsimageFile = "";

    public FSImageCheckpointer(BackupNode backupNode, FSNamesystem namesystem) {
        this.backupNode = backupNode;
        this.namesystem = namesystem;
    }

    @Override
    public void run() {
        System.out.println("fsimage checkpoint定时调度线程启动......");
        while (backupNode.shouldRunning()) {
            try {

                Thread.sleep(CHECKPOINT_INTERVAL);

                //触发checkpoint操作，把内存里面的数据写入到磁盘中
                removeLastFsimageFile();
                FSImage fsImage = namesystem.getFSImage();
                doCheckpoint(fsImage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将fsimage持久化到磁盘上去
     * @param fsImage
     * @throws Exception
     */
    private void doCheckpoint(FSImage fsImage) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(fsImage.getFsiamgeJson().getBytes());

        String fsimageFilePath = "D:\\dfs\\backupnode\\fsimage-"
                + fsImage.getMaxTxid() + ".meta";

        lastFsimageFile = fsimageFilePath;

        RandomAccessFile file = null;
        FileOutputStream out = null;
        FileChannel channel = null;

        try {
            file = new RandomAccessFile(fsimageFilePath, "rw");
            out = new FileOutputStream(file.getFD());
            channel = out.getChannel();

            channel.write(buffer);
            channel.force(false);
        } finally {
            if (out != null) {
                out.close();
            }
            if (file != null) {
                file.close();
            }
            if (channel != null) {
                channel.close();
            }
        }
    }

    /**
     * 删除上一个fsimage磁盘文件
     * @throws Exception
     */
    private void removeLastFsimageFile() throws Exception {
        File file = new File(lastFsimageFile);
        if (file.exists()) {
            file.delete();
        }
    }
}
