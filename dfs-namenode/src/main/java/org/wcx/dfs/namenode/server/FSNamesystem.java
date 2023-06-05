package org.wcx.dfs.namenode.server;

import java.util.HashMap;

/**
 * 管理元数据的核心组件
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

    public Boolean mkdir(String path) throws Exception{
        this.directory.mkdir(path);
        this.editlog.logEdit("{'OP':'MKDIR','PATH':'" + path + "'}");
        return true;
    }

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
}
