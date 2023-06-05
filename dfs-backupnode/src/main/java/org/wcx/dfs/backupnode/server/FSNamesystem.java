package org.wcx.dfs.backupnode.server;

/**
 * @author wcx
 * @date 2023/5/19 17:17
 */
public class FSNamesystem {

    private FSDirectory directory;

    public FSNamesystem() {
        this.directory = new FSDirectory();
    }

    public Boolean mkdir(long txid, String path) throws Exception {
        this.directory.mkdir(txid, path);
        return true;
    }

    public FSImage getFSImage() throws Exception {
        return directory.getFSImage();
    }

}
