package org.wcx.dfs.namenode.server;

/**
 * @author wcx
 * @date 2023/5/4 17:22
 */
public class FSNamesystem {
    private FSDirectory directory;

    private FSEditlog editlog;

    public FSNamesystem() {
        this.directory = new FSDirectory();
        this.editlog = new FSEditlog();
    }

    public Boolean mkdir(String path) {
        this.directory.mkdir(path);
//        this.editlog.logEdit("");
        return true;
    }
}
