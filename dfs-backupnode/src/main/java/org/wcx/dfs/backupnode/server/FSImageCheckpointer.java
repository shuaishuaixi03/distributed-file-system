package org.wcx.dfs.backupnode.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * backupnode执行checkpoint操作生成image文件的组件
 * @author wcx
 * @date 2023/6/4 20:29
 */
public class FSImageCheckpointer extends Thread{
    /**
     * checkpoint操作的时间间隔
     */
    public static final Integer CHECKPOINT_INTERVAL = 60 * 1000;

    private BackupNode backupNode;
    private FSNamesystem namesystem;

    private NameNodeRpcClient namenode;

    //上一次执行完checkpoint操作后存放的fsimage文件路径地址
    private String lastFsimageFile = "";
    //上一次开始执行checkpoint操作的时间点
    private long checkpointTime = System.currentTimeMillis();

    public FSImageCheckpointer(BackupNode backupNode, FSNamesystem namesystem, NameNodeRpcClient namenode) {
        this.backupNode = backupNode;
        this.namesystem = namesystem;
        this.namenode = namenode;
    }

    @Override
    public void run() {
        System.out.println("fsimage checkpoint定时调度线程启动......");
        while (backupNode.shouldRunning()) {
            try {

                if (!namesystem.finshedRecover()) {
                    System.out.println("当前还没完成元数据恢复，不进行checkpoint......");
                    Thread.sleep(1000);
                    continue;
                }

                if (lastFsimageFile.equals("")) {
                    this.lastFsimageFile = namesystem.getCheckpointFile();
                }

                long now = System.currentTimeMillis();
                if (now - checkpointTime > CHECKPOINT_INTERVAL) {
                    if (!namenode.getShouldNamenodeRunninng()) {
                        System.out.println("namenode当前无法访问，不执行checkpoint......");
                        continue;
                    }

                    //触发checkpoint操作，把内存里面的数据写入磁盘
                    System.out.println("准备执行checkpoint操作，写入fsimage文件......");
                    doCheckpoint();
                    System.out.println("完成checkpoint操作.......");
                }
                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 把fsiamge文件持久化到磁盘上
     * @throws Exception
     */
    private void doCheckpoint() throws Exception {
        FSImage fsimage = namesystem.getFSImage();

        removeLastFsimageFile();

        writeFSImageFile(fsimage);

        uploadFSImageFile(fsimage);

        updateCheckpointTxid(fsimage);

        saveCheckpointInfo(fsimage);
    }

    private void saveCheckpointInfo(FSImage fsimage) {
        String path = "D:\\dfs\\backupnode\\checkpoint-info.meta";

        RandomAccessFile raf = null;
        FileOutputStream out = null;
        FileChannel channel = null;

        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }

            long now = System.currentTimeMillis();
            this.checkpointTime = now;
            long checkpointTxid = fsimage.getMaxTxid();
            ByteBuffer buffer = ByteBuffer.wrap(String.valueOf(now + "_" + checkpointTxid + "_" + lastFsimageFile).getBytes());

            raf = new RandomAccessFile(path, "rw");
            out = new FileOutputStream(raf.getFD());
            channel = out.getChannel();

            channel.write(buffer);
            channel.force(false);

            System.out.println("checkpoint信息持久化到磁盘文件......");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (raf != null) {
                    raf.close();
                }
                if (channel != null) {
                    channel.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 跟新checkpoint txid
     * @param fsimage
     */
    private void updateCheckpointTxid(FSImage fsimage) {
        namenode.updateCheckpointTxid(fsimage.getMaxTxid());
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

    /**
     * 写入最新的fsimage文件
     * @param fsimage
     * @throws Exception
     */
    private void writeFSImageFile(FSImage fsimage) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(fsimage.getFsiamgeJson().getBytes());

        String fsimageFilePath = "D:\\dfs\\backupnode\\fsimage-"
                + fsimage.getMaxTxid() + ".meta";

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
     * 上传fsimage文件
     * @param fsimage
     * @throws Exception
     */
    private void uploadFSImageFile(FSImage fsimage) throws Exception {
        FSImageUploader fsimageUploader = new FSImageUploader(fsimage);
        fsimageUploader.start();
    }
}
