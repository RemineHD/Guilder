package dev.remine.guilder.rpc.loadbalancer;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 *This service is the handshake, this will be the first request made by a Client (Spigot/Proxy)
 *It will provide useful information, for example: it will provide the IP's, ports and tokens of all the services to a Client.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: loadbalancer/handshake.proto")
public final class HandshakeGrpc {

  private HandshakeGrpc() {}

  public static final String SERVICE_NAME = "Loadbalancer.Handshake";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.HandshakeRequest,
      dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.ClientHandshakeReply> METHOD_HANDSHAKE_CLIENT =
      io.grpc.MethodDescriptor.<dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.HandshakeRequest, dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.ClientHandshakeReply>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "Loadbalancer.Handshake", "HandshakeClient"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.HandshakeRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.ClientHandshakeReply.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HandshakeStub newStub(io.grpc.Channel channel) {
    return new HandshakeStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HandshakeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new HandshakeBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HandshakeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new HandshakeFutureStub(channel);
  }

  /**
   * <pre>
   *This service is the handshake, this will be the first request made by a Client (Spigot/Proxy)
   *It will provide useful information, for example: it will provide the IP's, ports and tokens of all the services to a Client.
   * </pre>
   */
  public static abstract class HandshakeImplBase implements io.grpc.BindableService {

    /**
     */
    public void handshakeClient(dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.HandshakeRequest request,
        io.grpc.stub.StreamObserver<dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.ClientHandshakeReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_HANDSHAKE_CLIENT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_HANDSHAKE_CLIENT,
            asyncUnaryCall(
              new MethodHandlers<
                dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.HandshakeRequest,
                dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.ClientHandshakeReply>(
                  this, METHODID_HANDSHAKE_CLIENT)))
          .build();
    }
  }

  /**
   * <pre>
   *This service is the handshake, this will be the first request made by a Client (Spigot/Proxy)
   *It will provide useful information, for example: it will provide the IP's, ports and tokens of all the services to a Client.
   * </pre>
   */
  public static final class HandshakeStub extends io.grpc.stub.AbstractStub<HandshakeStub> {
    private HandshakeStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HandshakeStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HandshakeStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HandshakeStub(channel, callOptions);
    }

    /**
     */
    public void handshakeClient(dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.HandshakeRequest request,
        io.grpc.stub.StreamObserver<dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.ClientHandshakeReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_HANDSHAKE_CLIENT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   *This service is the handshake, this will be the first request made by a Client (Spigot/Proxy)
   *It will provide useful information, for example: it will provide the IP's, ports and tokens of all the services to a Client.
   * </pre>
   */
  public static final class HandshakeBlockingStub extends io.grpc.stub.AbstractStub<HandshakeBlockingStub> {
    private HandshakeBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HandshakeBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HandshakeBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HandshakeBlockingStub(channel, callOptions);
    }

    /**
     */
    public dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.ClientHandshakeReply handshakeClient(dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.HandshakeRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_HANDSHAKE_CLIENT, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *This service is the handshake, this will be the first request made by a Client (Spigot/Proxy)
   *It will provide useful information, for example: it will provide the IP's, ports and tokens of all the services to a Client.
   * </pre>
   */
  public static final class HandshakeFutureStub extends io.grpc.stub.AbstractStub<HandshakeFutureStub> {
    private HandshakeFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HandshakeFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HandshakeFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HandshakeFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.ClientHandshakeReply> handshakeClient(
        dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.HandshakeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_HANDSHAKE_CLIENT, getCallOptions()), request);
    }
  }

  private static final int METHODID_HANDSHAKE_CLIENT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HandshakeImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HandshakeImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HANDSHAKE_CLIENT:
          serviceImpl.handshakeClient((dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.HandshakeRequest) request,
              (io.grpc.stub.StreamObserver<dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.ClientHandshakeReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class HandshakeDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return dev.remine.guilder.rpc.loadbalancer.HandshakeOuterClass.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HandshakeGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HandshakeDescriptorSupplier())
              .addMethod(METHOD_HANDSHAKE_CLIENT)
              .build();
        }
      }
    }
    return result;
  }
}
