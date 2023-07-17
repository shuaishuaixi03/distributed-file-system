// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: NameNodeRpcModel.proto

package org.wcx.dfs.namenode.rpc.model;

/**
 * Protobuf type {@code org.wcx.dfs.namenode.rpc.AllocateDataNodesResponse}
 */
public  final class AllocateDataNodesResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.wcx.dfs.namenode.rpc.AllocateDataNodesResponse)
    AllocateDataNodesResponseOrBuilder {
  // Use AllocateDataNodesResponse.newBuilder() to construct.
  private AllocateDataNodesResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private AllocateDataNodesResponse() {
    datanodes_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private AllocateDataNodesResponse(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            datanodes_ = s;
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesResponse_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse.class, org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse.Builder.class);
  }

  public static final int DATANODES_FIELD_NUMBER = 1;
  private volatile java.lang.Object datanodes_;
  /**
   * <code>optional string datanodes = 1;</code>
   */
  public java.lang.String getDatanodes() {
    java.lang.Object ref = datanodes_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      datanodes_ = s;
      return s;
    }
  }
  /**
   * <code>optional string datanodes = 1;</code>
   */
  public com.google.protobuf.ByteString
      getDatanodesBytes() {
    java.lang.Object ref = datanodes_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      datanodes_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getDatanodesBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, datanodes_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getDatanodesBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, datanodes_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse)) {
      return super.equals(obj);
    }
    org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse other = (org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse) obj;

    boolean result = true;
    result = result && getDatanodes()
        .equals(other.getDatanodes());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + DATANODES_FIELD_NUMBER;
    hash = (53 * hash) + getDatanodes().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code org.wcx.dfs.namenode.rpc.AllocateDataNodesResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.wcx.dfs.namenode.rpc.AllocateDataNodesResponse)
      org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse.class, org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse.Builder.class);
    }

    // Construct using org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      datanodes_ = "";

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_AllocateDataNodesResponse_descriptor;
    }

    public org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse getDefaultInstanceForType() {
      return org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse.getDefaultInstance();
    }

    public org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse build() {
      org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse buildPartial() {
      org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse result = new org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse(this);
      result.datanodes_ = datanodes_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse) {
        return mergeFrom((org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse other) {
      if (other == org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse.getDefaultInstance()) return this;
      if (!other.getDatanodes().isEmpty()) {
        datanodes_ = other.datanodes_;
        onChanged();
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object datanodes_ = "";
    /**
     * <code>optional string datanodes = 1;</code>
     */
    public java.lang.String getDatanodes() {
      java.lang.Object ref = datanodes_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        datanodes_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>optional string datanodes = 1;</code>
     */
    public com.google.protobuf.ByteString
        getDatanodesBytes() {
      java.lang.Object ref = datanodes_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        datanodes_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string datanodes = 1;</code>
     */
    public Builder setDatanodes(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      datanodes_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string datanodes = 1;</code>
     */
    public Builder clearDatanodes() {
      
      datanodes_ = getDefaultInstance().getDatanodes();
      onChanged();
      return this;
    }
    /**
     * <code>optional string datanodes = 1;</code>
     */
    public Builder setDatanodesBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      datanodes_ = value;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:org.wcx.dfs.namenode.rpc.AllocateDataNodesResponse)
  }

  // @@protoc_insertion_point(class_scope:org.wcx.dfs.namenode.rpc.AllocateDataNodesResponse)
  private static final org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse();
  }

  public static org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AllocateDataNodesResponse>
      PARSER = new com.google.protobuf.AbstractParser<AllocateDataNodesResponse>() {
    public AllocateDataNodesResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new AllocateDataNodesResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<AllocateDataNodesResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AllocateDataNodesResponse> getParserForType() {
    return PARSER;
  }

  public org.wcx.dfs.namenode.rpc.model.AllocateDataNodesResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
