package org.wcx.dfs.namenode.server;

/**
 * @author wangchengxi
 * @date 2023/7/17 10:20
 */
public class EditLogFactory {
    public static String mkdir(String path) {
        return "{'OP':'MKDIR', 'PATH':'" + path + "'}";
    }
    public static String create(String path) {
        return "{'OP':'CREATE', 'PATH':'" + path + "'}";
    }
}
