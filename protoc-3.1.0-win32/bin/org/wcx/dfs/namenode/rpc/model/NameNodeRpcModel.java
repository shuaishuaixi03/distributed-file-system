// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: NameNodeRpcModel.proto

package org.wcx.dfs.namenode.rpc.model;

public final class NameNodeRpcModel {
  private NameNodeRpcModel() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_RegisterRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_RegisterRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_RegisterResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_RegisterResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_HeartbeatRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_HeartbeatRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_HeartbeatResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_HeartbeatResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_MkdirRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_MkdirRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_MkdirResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_MkdirResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_ShutdownRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_ShutdownRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_ShutdownResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_ShutdownResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_FetchEditsLogRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_FetchEditsLogRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_FetchEditsLogResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_FetchEditsLogResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_UpdateCheckpointTxidRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_UpdateCheckpointTxidRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_UpdateCheckpointTxidResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_UpdateCheckpointTxidResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_CreateFileRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_CreateFileRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_CreateFileResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_CreateFileResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\026NameNodeRpcModel.proto\022\030org.wcx.dfs.na" +
      "menode.rpc\"/\n\017RegisterRequest\022\n\n\002ip\030\001 \001(" +
      "\t\022\020\n\010hostname\030\002 \001(\t\"\"\n\020RegisterResponse\022" +
      "\016\n\006status\030\001 \001(\005\"0\n\020HeartbeatRequest\022\n\n\002i" +
      "p\030\001 \001(\t\022\020\n\010hostname\030\002 \001(\t\"#\n\021HeartbeatRe" +
      "sponse\022\016\n\006status\030\001 \001(\005\"\034\n\014MkdirRequest\022\014" +
      "\n\004path\030\001 \001(\t\"\037\n\rMkdirResponse\022\016\n\006status\030" +
      "\001 \001(\005\"\037\n\017ShutdownRequest\022\014\n\004code\030\001 \001(\005\"\"" +
      "\n\020ShutdownResponse\022\016\n\006status\030\001 \001(\005\"*\n\024Fe" +
      "tchEditsLogRequest\022\022\n\nsyncedTxid\030\001 \001(\003\")",
      "\n\025FetchEditsLogResponse\022\020\n\010editsLog\030\001 \001(" +
      "\t\"+\n\033UpdateCheckpointTxidRequest\022\014\n\004txid" +
      "\030\001 \001(\003\".\n\034UpdateCheckpointTxidResponse\022\016" +
      "\n\006status\030\001 \001(\005\"%\n\021CreateFileRequest\022\020\n\010f" +
      "ilename\030\001 \001(\t\"$\n\022CreateFileResponse\022\016\n\006s" +
      "tatus\030\001 \001(\005\">\n\030AllocateDataNodesRequest\022" +
      "\020\n\010filename\030\001 \001(\t\022\020\n\010fileSize\030\002 \001(\003\".\n\031A" +
      "llocateDataNodesResponse\022\021\n\tdatanodes\030\001 " +
      "\001(\tB4\n\036org.wcx.dfs.namenode.rpc.modelB\020N" +
      "ameNodeRpcModelP\001b\006proto3"
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
        }, assigner);
    internal_static_org_wcx_dfs_namenode_rpc_RegisterRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_org_wcx_dfs_namenode_rpc_RegisterRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_RegisterRequest_descriptor,
        new java.lang.String[] { "Ip", "Hostname", });
    internal_static_org_wcx_dfs_namenode_rpc_RegisterResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_org_wcx_dfs_namenode_rpc_RegisterResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_RegisterResponse_descriptor,
        new java.lang.String[] { "Status", });
    internal_static_org_wcx_dfs_namenode_rpc_HeartbeatRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_org_wcx_dfs_namenode_rpc_HeartbeatRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_HeartbeatRequest_descriptor,
        new java.lang.String[] { "Ip", "Hostname", });
    internal_static_org_wcx_dfs_namenode_rpc_HeartbeatResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_org_wcx_dfs_namenode_rpc_HeartbeatResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_HeartbeatResponse_descriptor,
        new java.lang.String[] { "Status", });
    internal_static_org_wcx_dfs_namenode_rpc_MkdirRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_org_wcx_dfs_namenode_rpc_MkdirRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_MkdirRequest_descriptor,
        new java.lang.String[] { "Path", });
    internal_static_org_wcx_dfs_namenode_rpc_MkdirResponse_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_org_wcx_dfs_namenode_rpc_MkdirResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_MkdirResponse_descriptor,
        new java.lang.String[] { "Status", });
    internal_static_org_wcx_dfs_namenode_rpc_ShutdownRequest_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_org_wcx_dfs_namenode_rpc_ShutdownRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_ShutdownRequest_descriptor,
        new java.lang.String[] { "Code", });
    internal_static_org_wcx_dfs_namenode_rpc_ShutdownResponse_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_org_wcx_dfs_namenode_rpc_ShutdownResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_ShutdownResponse_descriptor,
        new java.lang.String[] { "Status", });
    internal_static_org_wcx_dfs_namenode_rpc_FetchEditsLogRequest_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_org_wcx_dfs_namenode_rpc_FetchEditsLogRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_FetchEditsLogRequest_descriptor,
        new java.lang.String[] { "SyncedTxid", });
    internal_static_org_wcx_dfs_namenode_rpc_FetchEditsLogResponse_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_org_wcx_dfs_namenode_rpc_FetchEditsLogResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_FetchEditsLogResponse_descriptor,
        new java.lang.String[] { "EditsLog", });
    internal_static_org_wcx_dfs_namenode_rpc_UpdateCheckpointTxidRequest_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_org_wcx_dfs_namenode_rpc_UpdateCheckpointTxidRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_UpdateCheckpointTxidRequest_descriptor,
        new java.lang.String[] { "Txid", });
    internal_static_org_wcx_dfs_namenode_rpc_UpdateCheckpointTxidResponse_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_org_wcx_dfs_namenode_rpc_UpdateCheckpointTxidResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_UpdateCheckpointTxidResponse_descriptor,
        new java.lang.String[] { "Status", });
    internal_static_org_wcx_dfs_namenode_rpc_CreateFileRequest_descriptor =
      getDescriptor().getMessageTypes().get(12);
    internal_static_org_wcx_dfs_namenode_rpc_CreateFileRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_CreateFileRequest_descriptor,
        new java.lang.String[] { "Filename", });
    internal_static_org_wcx_dfs_namenode_rpc_CreateFileResponse_descriptor =
      getDescriptor().getMessageTypes().get(13);
    internal_static_org_wcx_dfs_namenode_rpc_CreateFileResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_CreateFileResponse_descriptor,
        new java.lang.String[] { "Status", });
    internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesRequest_descriptor =
      getDescriptor().getMessageTypes().get(14);
    internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesRequest_descriptor,
        new java.lang.String[] { "Filename", "FileSize", });
    internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesResponse_descriptor =
      getDescriptor().getMessageTypes().get(15);
    internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesResponse_descriptor,
        new java.lang.String[] { "Datanodes", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
