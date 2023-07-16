package org.wcx.dfs.namenode.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


import com.alibaba.fastjson.JSONObject;
import org.wcx.dfs.namenode.server.FSDirectory.INode;

/**
 * 管理元数据的核心组件
 * @author wcx
 * @date 2023/5/4 17:22
 */
public class FSNamesystem {

    /**
     * 负责管理内存文件目录树的组件
     */
    private FSDirectory directory;

    /**
     * 负责管理editslog写入磁盘的组件
     */
    private FSEditlog editlog;

    /**
     * 上一次checkpoint更新到的txid
     */
    private long checkpointTxid;

    public FSNamesystem() {
        this.directory = new FSDirectory();
        this.editlog = new FSEditlog(this);
        recoverNamespace();
    }

    /**
     * 创建目录
     * @param path 目录路径
     * @return 是否成功
     * @throws Exception
     */
    public Boolean mkdir(String path) throws Exception{
        this.directory.mkdir(path);
        this.editlog.logEdit("{'OP':'MKDIR','PATH':'" + path + "'}");
        return true;
    }

    /**
     * 创建文件
     * @param filename 文件名，包含所在的路径
     * @return
     * @throws Exception
     */
    public Boolean create(String filename) throws Exception {
        if (!directory.create(filename)) {
            return false;
        }
        //TODO 这里写一条editlog
        return true;
    }

    /**
     * 强制把内存里的edits log刷入磁盘中
     */
    public void flush() {
        this.editlog.flush();
    }

    /**
     * 获取一个FSEditLog组件
     * @return
     */
    public FSEditlog getEditlog() {
        return editlog;
    }

    public void setCheckpointTxid(long txid) {
        System.out.println("接受到checkpoint txid: " + txid);
        this.checkpointTxid = txid;
    }

    public long getCheckpointTxid() {
        return checkpointTxid;
    }

    /**
     * 保存checkpoint txid到磁盘上去
     */
    public void saveCheckpointTxid() {
        String path = "D:\\dfs\\editslog\\checkpoint-txid.meta";

        RandomAccessFile raf = null;
        FileOutputStream out = null;
        FileChannel channel = null;

        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }

            ByteBuffer buffer = ByteBuffer.wrap(String.valueOf(checkpointTxid).getBytes());

            raf = new RandomAccessFile(path, "rw");
            out = new FileOutputStream(raf.getFD());
            channel = out.getChannel();

            channel.write(buffer);
            channel.force(false);
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
     * 恢复元数据
     */
    public void recoverNamespace() {
        try {
            loadFSImage();
            loadCheckpointTxid();
            loadEditLog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载fsimage文件到内存里面来进行恢复
     * @throws Exception
     */
    private void loadFSImage() throws Exception {
        FileInputStream in = null;
        FileChannel channel = null;
        try {
            String path = "D:\\dfs\\editslog\\fsimage.meta";

            File file = new File(path);
            if (!file.exists()) {
                System.out.println("fsimage文件当前不存在，不进行恢复......");
                return;
            }

            in = new FileInputStream(path);
            channel = in.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);



            int count = channel.read(buffer);

            buffer.flip();

            String fsimageJson = new String(buffer.array(), 0, count);

            System.out.println("恢复fsimage文件中的数据: " + fsimageJson);

            INode dirTree = JSONObject.parseObject(fsimageJson, INode.class);
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

    /**
     * 加载和回放editslog日志文件
     * @throws Exception
     */

    private void loadEditLog() throws Exception {
        File dir = new File("D:\\dfs\\editslog\\");

        List<File> files = new ArrayList<>();
        for (File file : dir.listFiles()) {
            files.add(file);
        }

        Iterator<File> fileIterator = files.iterator();
        while (fileIterator.hasNext()) {
            File file = fileIterator.next();
            if (!file.getName().contains("edits")) {
                fileIterator.remove();
            }
        }

        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                Integer o1StartTxid = Integer.valueOf(o1.getName().split("-")[1]);
                Integer o2StartTxid = Integer.valueOf(o2.getName().split("-")[1]);
                return o1StartTxid - o2StartTxid;
            }
        });

        if (files == null || files.size() == 0) {
            System.out.println("当前没有任何ecditslog文件，不进行恢复......");
        }

        for (File file : files) {
            if (file.getName().contains("edits")) {
                System.out.println("准备恢复editslog文件中的数据: " + file.getName());

                String[] splitedName = file.getName().split("-");
                long startTxid = Long.valueOf(splitedName[1]);
                long endTxid = Long.valueOf(splitedName[2].split("[.]")[0]);

                if (endTxid > checkpointTxid) {
                    String currentEditsLogFile = "D:\\dfs\\editslog\\edits-"
                            + startTxid + "-" + endTxid + ".log";
                    List<String> editsLogs = Files.readAllLines(Paths.get(currentEditsLogFile),
                            StandardCharsets.UTF_8);
                    for (String editLogJson : editsLogs) {
                        JSONObject editLog = JSONObject.parseObject(editLogJson);
                        long txid = editLog.getLongValue("txid");

                        if (txid > checkpointTxid) {
                            System.out.println("准备回放editlog: " + editLogJson);

                            String op = editLog.getString("OP");

                            if (op.equals("MKDIR")) {
                                String path = editLog.getString("PATH");
                                try {
                                    directory.mkdir(path);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 加载checkpoint到内存中
     * @throws Exception
     */
    private void loadCheckpointTxid() throws Exception {
        FileInputStream in = null;
        FileChannel channel = null;
        try {
            String path = "D:\\dfs\\editslog\\checkpoint-txid.meta";
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("checkpoint txid文件不存在, 不进行恢复");
                return;
            }
            in = new FileInputStream(path);
            channel = in.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);

            buffer.flip();
            long checkpointTxid = Long.valueOf(new String(buffer.array(), 0, count));
            System.out.println("恢复checkpoint txid: " + checkpointTxid);

            this.checkpointTxid = checkpointTxid;
        } finally {
            if (in != null) {
                in.close();
            }
            if (channel != null) {
                channel.close();
            }

        }
    }


}
