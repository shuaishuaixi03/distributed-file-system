package org.wcx.dfs.backupnode.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 从namenode同步editslog的组件
 * @author wcx
 * @date 2023/5/19 17:15
 */
public class EditsLogFetcher extends Thread {

    public static final Integer BACKUP_NODE_FETCH_SIZE = 10;

    private BackupNode backupNode;
    private NameNodeRpcClient namenode;

    private FSNamesystem namesystem;

    public EditsLogFetcher(BackupNode backupNode, FSNamesystem namesystem) {
        this.backupNode = backupNode;
        this.namenode = new NameNodeRpcClient();
        this.namesystem = namesystem;
    }

    @Override
    public void run() {
        System.out.println("Editslog抓取线程已经启动......");
        while (backupNode.shouldRunning()) {
            try {
                JSONArray editsLogs = namenode.fetchEditsLog();
                if (editsLogs.size() == 0) {
                    System.out.println("没有拉取到如何一条editsslog，等待1s");
                    Thread.sleep(1000);
                    continue;
                }
                if (editsLogs.size() < BACKUP_NODE_FETCH_SIZE) {
                    Thread.sleep(1000);
                    System.out.println("拉取到的edits log不足10条数据，等待1s后再次继续去拉取");
                }

                for (int i = 0; i < editsLogs.size(); i ++) {
                    JSONObject editsLog = editsLogs.getJSONObject(i);
                    System.out.println("拉取到一条editslog: " + editsLogs.toJSONString());
                    String op = editsLog.getString("OP");

                    if (op.equals("MKDIR")) {
                        String path = editsLog.getString("PATH");
                        try {
                            namesystem.mkdir(editsLog.getLongValue("txid"), path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
