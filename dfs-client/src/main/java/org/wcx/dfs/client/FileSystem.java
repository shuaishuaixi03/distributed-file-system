package org.wcx.dfs.client;

/**
 * @author wcx
 * @date 2023/5/17 19:15
 */
public interface FileSystem {
    void mkdir(String path) throws Exception;

    void shutdown() throws Exception;

}
