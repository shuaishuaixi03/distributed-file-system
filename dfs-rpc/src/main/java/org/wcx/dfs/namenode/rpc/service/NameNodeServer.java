// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: NameNodeRpcServer.proto

package org.wcx.dfs.namenode.rpc.service;

public final class NameNodeServer {
  private NameNodeServer() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\027NameNodeRpcServer.proto\022\030org.wcx.dfs.n" +
      "amenode.rpc\032\026NameNodeRpcModel.proto2\221\005\n\017" +
      "NameNodeService\022a\n\010register\022).org.wcx.df" +
      "s.namenode.rpc.RegisterRequest\032*.org.wcx" +
      ".dfs.namenode.rpc.RegisterResponse\022d\n\the" +
      "artbeat\022*.org.wcx.dfs.namenode.rpc.Heart" +
      "beatRequest\032+.org.wcx.dfs.namenode.rpc.H" +
      "eartbeatResponse\022X\n\005mkdir\022&.org.wcx.dfs." +
      "namenode.rpc.MkdirRequest\032\'.org.wcx.dfs." +
      "namenode.rpc.MkdirResponse\022a\n\010shutdown\022)",
      ".org.wcx.dfs.namenode.rpc.ShutdownReques" +
      "t\032*.org.wcx.dfs.namenode.rpc.ShutdownRes" +
      "ponse\022p\n\rfetchEditsLog\022..org.wcx.dfs.nam" +
      "enode.rpc.FetchEditsLogRequest\032/.org.wcx" +
      ".dfs.namenode.rpc.FetchEditsLogResponse\022" +
      "\205\001\n\024updateCheckpointTxid\0225.org.wcx.dfs.n" +
      "amenode.rpc.UpdateCheckpointTxidRequest\032" +
      "6.org.wcx.dfs.namenode.rpc.UpdateCheckpo" +
      "intTxidResponseB4\n org.wcx.dfs.namenode." +
      "rpc.serviceB\016NameNodeServerP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.getDescriptor(),
        }, assigner);
    org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
