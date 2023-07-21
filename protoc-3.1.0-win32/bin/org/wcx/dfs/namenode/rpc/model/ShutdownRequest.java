// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: NameNodeRpcModel.proto

package org.wcx.dfs.namenode.rpc.model;

/**
 * Protobuf type {@code org.wcx.dfs.namenode.rpc.ShutdownRequest}
 */
public  final class ShutdownRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.wcx.dfs.namenode.rpc.ShutdownRequest)
    ShutdownRequestOrBuilder {
  // Use ShutdownRequest.newBuilder() to construct.
  private ShutdownRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ShutdownRequest() {
    code_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private ShutdownRequest(
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

            code_ = input.readInt32();
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
    return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_ShutdownRequest_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_ShutdownRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.wcx.dfs.namenode.rpc.model.ShutdownRequest.class, org.wcx.dfs.namenode.rpc.model.ShutdownRequest.Builder.class);
  }

  public static final int CODE_FIELD_NUMBER = 1;
  private int code_;
  /**
   * <code>optional int32 code = 1;</code>
   */
  public int getCode() {
    return code_;
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
    if (code_ != 0) {
      output.writeInt32(1, code_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (code_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, code_);
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
    if (!(obj instanceof org.wcx.dfs.namenode.rpc.model.ShutdownRequest)) {
      return super.equals(obj);
    }
    org.wcx.dfs.namenode.rpc.model.ShutdownRequest other = (org.wcx.dfs.namenode.rpc.model.ShutdownRequest) obj;

    boolean result = true;
    result = result && (getCode()
        == other.getCode());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + CODE_FIELD_NUMBER;
    hash = (53 * hash) + getCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.wcx.dfs.namenode.rpc.model.ShutdownRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.wcx.dfs.namenode.rpc.model.ShutdownRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.wcx.dfs.namenode.rpc.model.ShutdownRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.wcx.dfs.namenode.rpc.model.ShutdownRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.wcx.dfs.namenode.rpc.model.ShutdownRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.wcx.dfs.namenode.rpc.model.ShutdownRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.wcx.dfs.namenode.rpc.model.ShutdownRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.wcx.dfs.namenode.rpc.model.ShutdownRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.wcx.dfs.namenode.rpc.model.ShutdownRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.wcx.dfs.namenode.rpc.model.ShutdownRequest parseFrom(
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
  public static Builder newBuilder(org.wcx.dfs.namenode.rpc.model.ShutdownRequest prototype) {
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
   * Protobuf type {@code org.wcx.dfs.namenode.rpc.ShutdownRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.wcx.dfs.namenode.rpc.ShutdownRequest)
      org.wcx.dfs.namenode.rpc.model.ShutdownRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_ShutdownRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_ShutdownRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.wcx.dfs.namenode.rpc.model.ShutdownRequest.class, org.wcx.dfs.namenode.rpc.model.ShutdownRequest.Builder.class);
    }

    // Construct using org.wcx.dfs.namenode.rpc.model.ShutdownRequest.newBuilder()
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
      code_ = 0;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.wcx.dfs.namenode.rpc.model.NameNodeRpcModel.internal_static_org_wcx_dfs_namenode_rpc_ShutdownRequest_descriptor;
    }

    public org.wcx.dfs.namenode.rpc.model.ShutdownRequest getDefaultInstanceForType() {
      return org.wcx.dfs.namenode.rpc.model.ShutdownRequest.getDefaultInstance();
    }

    public org.wcx.dfs.namenode.rpc.model.ShutdownRequest build() {
      org.wcx.dfs.namenode.rpc.model.ShutdownRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.wcx.dfs.namenode.rpc.model.ShutdownRequest buildPartial() {
      org.wcx.dfs.namenode.rpc.model.ShutdownRequest result = new org.wcx.dfs.namenode.rpc.model.ShutdownRequest(this);
      result.code_ = code_;
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
      if (other instanceof org.wcx.dfs.namenode.rpc.model.ShutdownRequest) {
        return mergeFrom((org.wcx.dfs.namenode.rpc.model.ShutdownRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.wcx.dfs.namenode.rpc.model.ShutdownRequest other) {
      if (other == org.wcx.dfs.namenode.rpc.model.ShutdownRequest.getDefaultInstance()) return this;
      if (other.getCode() != 0) {
        setCode(other.getCode());
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
      org.wcx.dfs.namenode.rpc.model.ShutdownRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.wcx.dfs.namenode.rpc.model.ShutdownRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int code_ ;
    /**
     * <code>optional int32 code = 1;</code>
     */
    public int getCode() {
      return code_;
    }
    /**
     * <code>optional int32 code = 1;</code>
     */
    public Builder setCode(int value) {
      
      code_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional int32 code = 1;</code>
     */
    public Builder clearCode() {
      
      code_ = 0;
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


    // @@protoc_insertion_point(builder_scope:org.wcx.dfs.namenode.rpc.ShutdownRequest)
  }

  // @@protoc_insertion_point(class_scope:org.wcx.dfs.namenode.rpc.ShutdownRequest)
  private static final org.wcx.dfs.namenode.rpc.model.ShutdownRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.wcx.dfs.namenode.rpc.model.ShutdownRequest();
  }

  public static org.wcx.dfs.namenode.rpc.model.ShutdownRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ShutdownRequest>
      PARSER = new com.google.protobuf.AbstractParser<ShutdownRequest>() {
    public ShutdownRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new ShutdownRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ShutdownRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ShutdownRequest> getParserForType() {
    return PARSER;
  }

  public org.wcx.dfs.namenode.rpc.model.ShutdownRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

