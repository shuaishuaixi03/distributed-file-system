package org.wcx.dfs.backupnode.server;

/**
 * @author wcx
 * @date 2023/5/19 17:15
 */
public class BackupNode {

    private volatile Boolean shouldRunning = true;
    private FSNamesystem namesystem;

    public static void main(String[] args) throws Exception {
        BackupNode backupNode = new BackupNode();
        backupNode.init();
        backupNode.start();
    }

    public void init() {
        this.namesystem = new FSNamesystem();
    }

    public void start() throws Exception {
        EditsLogFetcher editsLogFetcher = new EditsLogFetcher(this, namesystem);
        editsLogFetcher.start();

        FSImageCheckpointer fsimageCheckpointer = new FSImageCheckpointer(this, namesystem);
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
