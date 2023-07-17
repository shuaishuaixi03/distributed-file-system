package org.wcx.dfs.backupnode.server;

import com.alibaba.fastjson.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 负责管理内存中文件目录树的组件
 * @author wcx
 * @date 2023/5/4 17:24
 */
public class FSDirectory {
    //表示文件目录树的根节点目录
    private INode dirTree;

    /**
     * 文件目录树的读写锁
     */
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 当前文件目录树更新到哪一个txid对应的editslog
     */
    private long maxTxid = 0;

    public void writeLock() {
        lock.writeLock().lock();
    }
    public void writeUnLock() {
        lock.writeLock().unlock();
    }
    public void readLock() {
        lock.readLock().lock();
    }
    public void readUnLock() {
        lock.readLock().unlock();
    }

    public FSDirectory() {
        this.dirTree = new INode("/");
    }

    /**
     * 以JSON格式获取fsimage内存元数据
     * @return
     */
    public FSImage getFSImage() {
        FSImage fsimage = null;
        try {
            readLock();
            String fsimageJson = JSONObject.toJSONString(dirTree);

            fsimage = new FSImage(maxTxid, fsimageJson);
        } finally {
            readUnLock();
        }
        return fsimage;
    }

    /**
     * 创建目录
     * @param txid
     * @param path 目录路径
     */
    public void mkdir(long txid, String path) {
        try {
            writeLock();

            maxTxid = txid;

            String[] pathes = path.split("/");
            INode parent = dirTree;

            for (String splitedPath : pathes) {
                if (splitedPath.trim().equals("")) {
                    continue;
                }
                INode dir = findDirectory(parent, splitedPath);
                if (dir != null) {
                    parent = dir;
                    continue;
                }
                INode child = new INode(splitedPath);
                parent.addChild(child);
                parent = child;
            }
        } finally {
            writeUnLock();
        }

//        printDirTree(dirTree, "");
    }

    /**
     * 创建文件
     * @param txid
     * @param filename
     * @return
     */
    public Boolean create(long txid, String filename) {
        try {
            writeLock();

            maxTxid = txid;

            String[] splitedFilename = filename.split("/");
            String realFilename = splitedFilename[splitedFilename.length - 1];

            INode parent = dirTree;

            //寻找文件的上一级目录
            for (int i = 0; i < splitedFilename.length - 1; i ++) {
                if (i == 0) {
                    continue;
                }
                INode dir = findDirectory(parent, splitedFilename[i]);

                if (dir != null) {
                    parent = dir;
                    continue;
                }
                INode child = new INode(splitedFilename[i]);
                parent.addChild(child);
                parent = child;
            }
            //判断目录下是否该文件是否已经存在
            if (existFile(parent, realFilename)) {
                return false;
            }
            INode file = new INode(realFilename);
            parent.addChild(file);
            return true;
        } finally {
            writeUnLock();
        }
    }

    private Boolean existFile(INode dirTree, String filename) {
        if (dirTree != null && dirTree.getChildren().size() > 0) {
            for (INode child : dirTree.getChildren()) {
                if (child.getPath().equals(filename)) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unused")
    private void printDirTree(INode dirTree, String separator) {
        if(dirTree.getChildren().size() == 0) {
            return;
        }
        for(INode dir : dirTree.getChildren()) {
            System.out.println(separator + ((INode) dir).getPath());
            printDirTree((INode) dir, separator + "-");
        }
    }

    /**
     * 以dir为根结点去查找子目录path
     * @param dir
     * @param path
     * @return
     */
    private INode findDirectory(INode dir, String path) {
        if (dir.getChildren().size() == 0) {
            return null;
        }
        for (INode child : dir.getChildren()) {
            if (child instanceof INode) {
                INode childDir = (INode) child;
                if ((childDir.getPath().equals(path))) {
                    return childDir;
                }
            }
        }
        return null;
    }

    public INode getDirTree() {
        return this.dirTree;
    }
    public void setDirTree(INode dirTree) {
        this.dirTree = dirTree;
    }
    public long getMaxTxid() {
        return this.maxTxid;
    }
    public void setMaxTxid(long maxTxid) {
        this.maxTxid = maxTxid;
    }

    /**
     * 代表文件目录树的一个目录
     */
    public static class INode {
        private String path;
        private List<INode> children;

        public INode() {

        }

        public INode(String path) {
            this.path = path;
            this.children = new LinkedList<>();
        }

        public void addChild(INode inode) {
            this.children.add(inode);
        }

        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }
        public List<INode> getChildren() {
            return children;
        }
        public void setChildren() {
            this.children = children;
        }

        @Override
        public String toString() {
            return "INode [path=" + path + ", children=" + children + "]";
        }

    }
}
