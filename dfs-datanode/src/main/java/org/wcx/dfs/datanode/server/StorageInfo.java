package org.wcx.dfs.datanode.server;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据节点存储的文件副本信息
 * @author wangchengxi
 * @date 2023/7/24 9:52
 */
public class StorageInfo {
    private List<String> filenames = new ArrayList<>();
    private Long storedDataSize = 0L;

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }

    public Long getStoredDataSize() {
        return storedDataSize;
    }

    public void setStoredDataSize(Long storedDataSize) {
        this.storedDataSize = storedDataSize;
    }

    public void addFilename(String filename) {
        this.filenames.add(filename);
    }

    public void addStoredDataSize(Long storedDataSize) {
        this.storedDataSize += storedDataSize;
    }
}
