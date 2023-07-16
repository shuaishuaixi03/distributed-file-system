package org.wcx.dfs.namenode.server;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wcx
 * @date 2023/5/4 17:24
 */
public class FSDirectory {

    /**
     * 内存中文件目录树的根节点
     */
    private INode dirTree;

    public FSDirectory() {
        this.dirTree = new INode("/");
    }

    /**
     * 创建目录
     * @param path 目录路径
     */
    public void mkdir(String path) {
        synchronized(dirTree) {
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
        }
    }

    /**
     * 创建文件
     * @param filename 文件名
     * @return
     */
    public Boolean create(String filename) {
        synchronized(dirTree) {
            String[] splitedFilename = filename.split("/");
            String realFilename = splitedFilename[splitedFilename.length - 1];

            INode parent = dirTree;

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

            if (existFile(parent, realFilename)) {
                return false;
            }

            INode file = new INode(realFilename);
            parent.addChild(file);
            return true;
        }
    }

    /**
     * 判断dir目录下是否存在filename文件
     * @param dir
     * @param filename
     * @return
     */
    private Boolean existFile(INode dir, String filename) {
        if (dir.getChildren() != null && dir.getChildren().size() > 0) {
            for (INode child : dir.getChildren()) {
                if (child.getPath().equals(filename)) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("uused")
    private void printDirTree(INode dirTree, String blank) {
        if (dirTree.getChildren().size() == 0) {
            return;
        }
        for (INode dir : dirTree.getChildren()) {
            System.out.println(blank + ((INode) dir).getPath());
            printDirTree((INode) dir, blank + " ");
        }
    }

    /**
     * 查找子目录
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
                if (childDir.getPath().equals(path)) {
                    return childDir;
                }
            }
        }
        return null;
    }


    public INode getDirTree() {
        return dirTree;
    }

    public void setDirTree(INode dirTree) {
        this.dirTree = dirTree;
    }


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
        public void setChildren(List<INode> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            return "INode [path=" + path + ", children=" + children + "]";
        }

    }
}
