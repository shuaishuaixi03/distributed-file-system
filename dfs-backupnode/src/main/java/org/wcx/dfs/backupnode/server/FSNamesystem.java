package org.wcx.dfs.backupnode.server;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.wcx.dfs.backupnode.server.FSDirectory.INode;

/**‘
 * 负责管理元数据的组件
 * @author wcx
 * @date 2023/5/19 17:17
 */
public class FSNamesystem {
    private FSDirectory directory;
    private long checkpointTime;
    private long syncedTxid;
    private String checkpointFile = "";

    //backupnode是否处于重启时的恢复数据阶段
    private volatile boolean finishedRecover = false;

    public FSNamesystem() {
        this.directory = new FSDirectory();
        recoverNamespace();
    }

    /**
     * 创建目录
     *
     * @param txid 目录路径
     * @param path 是否成功
     * @return
     * @throws Exception
     */
    public Boolean mkdir(long txid, String path) throws Exception {
        this.directory.mkdir(txid, path);
        return true;
    }

    /**
     * 创建文件
     * @param txid
     * @param filename 文件名，包含所在路径
     * @return
     * @throws Exception
     */
    public Boolean create(long txid, String filename) throws Exception {
        if (!directory.create(txid, filename)) {
            return false;
        }
        return true;
    }

    /**
     * 以json数据格式获取文件目录树
     *
     * @return
     * @throws Exception
     */
    public FSImage getFSImage() throws Exception {
        return directory.getFSImage();
    }

    /**
     * 获取当前同步到的最大txid
     *
     * @return
     * @throws Exception
     */
    public long getSyncedTxid() throws Exception {
        return directory.getFSImage().getMaxTxid();
    }

    /**
     * 恢复元数据
     */
    public void recoverNamespace() {
        try {
            loadCheckpointInfo();
            loadFSImage();
            finishedRecover = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载checkpoint txid信息
     * @throws Exception
     */
    private void loadCheckpointInfo() throws Exception {
        FileInputStream in = null;
        FileChannel channel = null;
        try {
            String path = "D:\\dfs\\backupnode\\checkpoint-info.meta";

            File file = new File(path);
            if (!file.exists()) {
                System.out.println("checkpoint info文件不存在，不进行恢复......");
                return;
            }

            in = new FileInputStream(path);
            channel = in.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);

            buffer.flip();

            String checkpointInfo = new String(buffer.array(), 0, count);
            long checkpointTime = Long.valueOf(checkpointInfo.split("-")[0]);
            long syncedTxid = Long.valueOf(checkpointInfo.split("-")[1]);
            String fsimageFile = checkpointInfo.split("-")[2];

            System.out.println("恢复checkpoint time" + checkpointTime + ", synced txid" + syncedTxid + ", fsimage file: " + fsimageFile);


            this.checkpointTime = checkpointTime;
            this.syncedTxid = syncedTxid;
            this.checkpointFile = fsimageFile;
            directory.setMaxTxid(syncedTxid);

        } finally {
            if (in != null) {
                in.close();
            }
            if (channel != null) {
                channel.close();
            }
        }
    }

    /**
     * 加载fsimage文件到内存中恢复文件目录树
     * @throws Exception
     */
    private void loadFSImage() throws Exception {
        FileInputStream in = null;
        FileChannel channel = null;
        try {
            String path = "D:\\dfs\\backupnode\\fsimage-" + syncedTxid + ".meta";
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("fsimage文件当前不存在, 不进行恢复......");
                return;
            }

            in = new FileInputStream(path);
            channel = in.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

            int count = channel.read(buffer);

            buffer.flip();
            String fsimageJson = new String(buffer.array(), 0, count);
            System.out.println("恢复fsimage文件中的数据: " + fsimageJson);

            INode dirTree = JSONObject.parseObject(fsimageJson, new TypeReference<INode>(){});
            System.out.println(dirTree);
            directory.setDirTree(dirTree);
        } finally {
            if (in != null) {
                in.close();
            }
            if (channel != null) {
                channel.close();
            }
        }
    }

    public long getCheckpointTime() {
        return checkpointTime;
    }

    public void setCheckpointTime(long checkpointTime) {
        this.checkpointTime = checkpointTime;
    }

    public void setSyncedTxid(long syncedTxid) {
        this.syncedTxid = syncedTxid;
    }

    public boolean finshedRecover() {
        return finishedRecover;
    }

    public void setFinshedRecover(boolean finishedRecover) {
        this.finishedRecover = finishedRecover;
    }

    public String getCheckpointFile() {
        return checkpointFile;
    }

    public void setCheckpointFile(String checkpointFile) {
        this.checkpointFile = checkpointFile;
    }


}
