package org.wcx.dfs.datanode.server;

import java.io.File;

/**
 * @author wangchengxi
 * @date 2023/7/24 17:24
 */
public class StorageManager {
    /**
     * 获取数据节点上的存储的文件副本信息
     * @return
     */
    private StorageInfo getStorageInfo() {
        StorageInfo storageInfo = new StorageInfo();

        File dataDir = new File(DataNodeConfig.DATA_DIR);
        File[] children = dataDir.listFiles();
        if (children == null || children.length == 0) {
            return null;
        }
        for (File child : children) {
            scanFiles(child, storageInfo);
        }
        return storageInfo;
    }


    /**
     * 扫描文件
     * @param dir
     * @param storageInfo
     */
    private void scanFiles(File dir, StorageInfo storageInfo) {
        if (dir.isFile()) {
            String path = dir.getPath();
            path = path.substring(DataNodeConfig.DATA_DIR.length() - 2);

            path = path.replace("\\", "/");

            storageInfo.addFilename(path);
            storageInfo.addStoredDataSize(dir.length());

            return;
        }
        File[] children = dir.listFiles();
        if (children == null || children.length == 0) {
            return;
        }
        for (File child : children) {
            scanFiles(child, storageInfo);
        }
    }
}
