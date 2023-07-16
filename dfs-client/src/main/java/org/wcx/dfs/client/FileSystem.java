package org.wcx.dfs.client;

/**
 * @author wcx
 * @date 2023/5/17 19:15
 */
public interface FileSystem {
    /**
     * 创建目录
     * @param path 目录对应的文件路径
     * @throws Exception
     */
    void mkdir(String path) throws Exception;

    /**
     * 优雅关闭
     * @throws Exception
     */
    void shutdown() throws Exception;

    /**
     * 上传文件
     * @param file 文件的字节数组
     * @param filename 文件名
     */
    void upload(byte[] file, String filename) throws Exception;

}
