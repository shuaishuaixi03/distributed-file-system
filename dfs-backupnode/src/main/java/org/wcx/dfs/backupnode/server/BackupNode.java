package org.wcx.dfs.backupnode.server;

/**
 *
 * @author wcx
 * @date 2023/5/19 17:15
 */
public class BackupNode {

    private volatile Boolean shouldRunning = true;
    //负责backupnode节点的元数据的组件
    private FSNamesystem namesystem;
    //负责调用namenode提供的接口的组件
    private NameNodeRpcClient namenode;

    public static void main(String[] args) throws Exception {
        BackupNode backupNode = new BackupNode();
        backupNode.init();
        backupNode.start();
    }

    public void init() {
        this.namesystem = new FSNamesystem();
        this.namenode = new NameNodeRpcClient();
    }

    public void start() throws Exception {
        EditsLogFetcher editsLogFetcher = new EditsLogFetcher(this, namesystem, namenode);
        editsLogFetcher.start();

        FSImageCheckpointer fsimageCheckpointer = new FSImageCheckpointer(this, namesystem, namenode);
        fsimageCheckpointer.start();
    }


    public void run() throws Exception {
        while (shouldRunning) {
            Thread.sleep(1000);
        }
    }

    public Boolean shouldRunning() {
        return shouldRunning;
    }

}
