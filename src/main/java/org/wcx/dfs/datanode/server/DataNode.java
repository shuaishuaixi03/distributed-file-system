package org.wcx.dfs.datanode.server;

/**
 * @author wcx
 * @date 2023/5/4 16:12
*/
public class DataNode {
    //表示datanode节点是否正在运行
    private volatile Boolean shouldRun;
    private NameNodeOfferService offerService;

    private void initialize() {
        this.shouldRun = true;
        this.offerService = new NameNodeOfferService();
        this.offerService.start();
    }

    private void run() {
        try {
            while (shouldRun) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DataNode datanode = new DataNode();
        datanode.initialize();
        datanode.run();
    }

}
