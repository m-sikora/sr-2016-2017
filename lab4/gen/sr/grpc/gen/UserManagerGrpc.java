package sr.grpc.gen;

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
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.2.0)",
    comments = "Source: lab.proto")
public final class UserManagerGrpc {

  private UserManagerGrpc() {}

  public static final String SERVICE_NAME = "lab.UserManager";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<sr.grpc.gen.RegistrationTicket,
      sr.grpc.gen.RegistrationResult> METHOD_REGISTER_USER =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "lab.UserManager", "RegisterUser"),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.RegistrationTicket.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.RegistrationResult.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserManagerStub newStub(io.grpc.Channel channel) {
    return new UserManagerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserManagerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new UserManagerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static UserManagerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new UserManagerFutureStub(channel);
  }

  /**
   */
  public static abstract class UserManagerImplBase implements io.grpc.BindableService {

    /**
     */
    public void registerUser(sr.grpc.gen.RegistrationTicket request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.RegistrationResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REGISTER_USER, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_REGISTER_USER,
            asyncUnaryCall(
              new MethodHandlers<
                sr.grpc.gen.RegistrationTicket,
                sr.grpc.gen.RegistrationResult>(
                  this, METHODID_REGISTER_USER)))
          .build();
    }
  }

  /**
   */
  public static final class UserManagerStub extends io.grpc.stub.AbstractStub<UserManagerStub> {
    private UserManagerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserManagerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserManagerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserManagerStub(channel, callOptions);
    }

    /**
     */
    public void registerUser(sr.grpc.gen.RegistrationTicket request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.RegistrationResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REGISTER_USER, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UserManagerBlockingStub extends io.grpc.stub.AbstractStub<UserManagerBlockingStub> {
    private UserManagerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserManagerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserManagerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserManagerBlockingStub(channel, callOptions);
    }

    /**
     */
    public sr.grpc.gen.RegistrationResult registerUser(sr.grpc.gen.RegistrationTicket request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REGISTER_USER, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UserManagerFutureStub extends io.grpc.stub.AbstractStub<UserManagerFutureStub> {
    private UserManagerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserManagerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserManagerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserManagerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sr.grpc.gen.RegistrationResult> registerUser(
        sr.grpc.gen.RegistrationTicket request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REGISTER_USER, getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_USER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UserManagerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UserManagerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER_USER:
          serviceImpl.registerUser((sr.grpc.gen.RegistrationTicket) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.RegistrationResult>) responseObserver);
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

  private static final class UserManagerDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return sr.grpc.gen.LabProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UserManagerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserManagerDescriptorSupplier())
              .addMethod(METHOD_REGISTER_USER)
              .build();
        }
      }
    }
    return result;
  }
}
