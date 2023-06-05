// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: NameNodeRpcModel.proto

package org.wcx.dfs.namenode.rpc.model;

/**
 * Protobuf type {@code org.wcx.dfs.namenode.rpc.MkdirResponse}
 */
public  final class MkdirResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.wcx.dfs.namenode.rpc.MkdirResponse)
    MkdirResponseOrBuilder {
  // Use MkdirResponse.newBuilder() to construct.
  private MkdirResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MkdirResponse() {
    status_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private MkdirResponse(
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
          case 8: {

            status_ = input.readInt32();
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
    return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_MkdirResponse_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_MkdirResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.wcx.dfs.namenode.rpc.model.MkdirResponse.class, org.wcx.dfs.namenode.rpc.model.MkdirResponse.Builder.class);
  }

  public static final int STATUS_FIELD_NUMBER = 1;
  private int status_;
  /**
   * <code>optional int32 status = 1;</code>
   */
  public int getStatus() {
    return status_;
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
    if (status_ != 0) {
      output.writeInt32(1, status_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (status_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, status_);
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
    if (!(obj instanceof org.wcx.dfs.namenode.rpc.model.MkdirResponse)) {
      return super.equals(obj);
    }
    org.wcx.dfs.namenode.rpc.model.MkdirResponse other = (org.wcx.dfs.namenode.rpc.model.MkdirResponse) obj;

    boolean result = true;
    result = result && (getStatus()
        == other.getStatus());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + STATUS_FIELD_NUMBER;
    hash = (53 * hash) + getStatus();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.wcx.dfs.namenode.rpc.model.MkdirResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.wcx.dfs.namenode.rpc.model.MkdirResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.wcx.dfs.namenode.rpc.model.MkdirResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.wcx.dfs.namenode.rpc.model.MkdirResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.wcx.dfs.namenode.rpc.model.MkdirResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.wcx.dfs.namenode.rpc.model.MkdirResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.wcx.dfs.namenode.rpc.model.MkdirResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.wcx.dfs.namenode.rpc.model.MkdirResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.wcx.dfs.namenode.rpc.model.MkdirResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.wcx.dfs.namenode.rpc.model.MkdirResponse parseFrom(
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
  public static Builder newBuilder(org.wcx.dfs.namenode.rpc.model.MkdirResponse prototype) {
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
   * Protobuf type {@code org.wcx.dfs.namenode.rpc.MkdirResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.wcx.dfs.namenode.rpc.MkdirResponse)
      org.wcx.dfs.namenode.rpc.model.MkdirResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_MkdirResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_MkdirResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.wcx.dfs.namenode.rpc.model.MkdirResponse.class, org.wcx.dfs.namenode.rpc.model.MkdirResponse.Builder.class);
    }

    // Construct using org.wcx.dfs.namenode.rpc.model.MkdirResponse.newBuilder()
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
      status_ = 0;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_MkdirResponse_descriptor;
    }

    public org.wcx.dfs.namenode.rpc.model.MkdirResponse getDefaultInstanceForType() {
      return org.wcx.dfs.namenode.rpc.model.MkdirResponse.getDefaultInstance();
    }

    public org.wcx.dfs.namenode.rpc.model.MkdirResponse build() {
      org.wcx.dfs.namenode.rpc.model.MkdirResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.wcx.dfs.namenode.rpc.model.MkdirResponse buildPartial() {
      org.wcx.dfs.namenode.rpc.model.MkdirResponse result = new org.wcx.dfs.namenode.rpc.model.MkdirResponse(this);
      result.status_ = status_;
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
      if (other instanceof org.wcx.dfs.namenode.rpc.model.MkdirResponse) {
        return mergeFrom((org.wcx.dfs.namenode.rpc.model.MkdirResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.wcx.dfs.namenode.rpc.model.MkdirResponse other) {
      if (other == org.wcx.dfs.namenode.rpc.model.MkdirResponse.getDefaultInstance()) return this;
      if (other.getStatus() != 0) {
        setStatus(other.getStatus());
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
      org.wcx.dfs.namenode.rpc.model.MkdirResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.wcx.dfs.namenode.rpc.model.MkdirResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int status_ ;
    /**
     * <code>optional int32 status = 1;</code>
     */
    public int getStatus() {
      return status_;
    }
    /**
     * <code>optional int32 status = 1;</code>
     */
    public Builder setStatus(int value) {
      
      status_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional int32 status = 1;</code>
     */
    public Builder clearStatus() {
      
      status_ = 0;
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


    // @@protoc_insertion_point(builder_scope:org.wcx.dfs.namenode.rpc.MkdirResponse)
  }

  // @@protoc_insertion_point(class_scope:org.wcx.dfs.namenode.rpc.MkdirResponse)
  private static final org.wcx.dfs.namenode.rpc.model.MkdirResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.wcx.dfs.namenode.rpc.model.MkdirResponse();
  }

  public static org.wcx.dfs.namenode.rpc.model.MkdirResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MkdirResponse>
      PARSER = new com.google.protobuf.AbstractParser<MkdirResponse>() {
    public MkdirResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new MkdirResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MkdirResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MkdirResponse> getParserForType() {
    return PARSER;
  }

  public org.wcx.dfs.namenode.rpc.model.MkdirResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

