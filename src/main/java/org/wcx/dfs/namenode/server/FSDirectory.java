package org.wcx.dfs.namenode.server;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wcx
 * @date 2023/5/4 17:24
 */
public class FSDirectory {
    //表示文件树的根节点目录
    private INodeDirectory dirTree;

    public FSDirectory() {
        this.dirTree = new INodeDirectory("/");
    }

    public void mkdir(String path) {
        synchronized(dirTree) {
            String[] pathes = path.split("/");
            INodeDirectory parent = dirTree;

            for (String splitedPath : pathes) {
                //TODO 判断创建的文件目录路径是否合法

                INodeDirectory dir = findDirectory(parent, splitedPath);
                if (dir != null) {
                    parent = dir;
                    continue;
                }
                INodeDirectory child = new INodeDirectory(splitedPath);
                parent.addChild(child);
            }
        }
    }

    private INodeDirectory findDirectory(INodeDirectory dir, String path) {
        if (dir.getChildren().size() == 0) {
            return null;
        }
        INodeDirectory resultDir = null;
        for (INode child : dir.getChildren()) {
            if (child instanceof INodeDirectory) {
                INodeDirectory childDir = (INodeDirectory) child;

                if ((childDir.getPath().equals(path))) {
                    return childDir;
                }
                resultDir = findDirectory(childDir, path);
                if (resultDir != null) {
                    return resultDir;
                }
            }
        }
        return null;
    }

    /**
     * 表示文件树中的一个节点
     */
    private interface INode {}

    /**
     * 表示文件树中的一个目录
     */
    public static class INodeDirectory implements INode {
        private String path;
        private List<INode> children;

        public INodeDirectory(String path) {
            this.path = path;
            this.children = new LinkedList<INode>();
        }

        public void addChild(INode inode) {
            this.children.add(inode);
        }
        public String getPath() {
            return path;
        }
        public List<INode> getChildren() {
            return children;
        }
        public void setChildren(List<INode> children) {
            this.children = children;
        }
    }

    /**
     * 表示文件树中的一个文件
     */
    public static class INodeFile implements INode {
        private String name;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
