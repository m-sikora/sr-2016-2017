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
public final class DeviceManagerGrpc {

  private DeviceManagerGrpc() {}

  public static final String SERVICE_NAME = "lab.DeviceManager";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<sr.grpc.gen.ListDevicesRequest,
      sr.grpc.gen.DeviceInfo> METHOD_LIST_DEVICES =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "lab.DeviceManager", "ListDevices"),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.ListDevicesRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.DeviceInfo.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<sr.grpc.gen.ControlDeviceRequest,
      sr.grpc.gen.ControlDeviceResponse> METHOD_CONTROL_DEVICE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "lab.DeviceManager", "ControlDevice"),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.ControlDeviceRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.ControlDeviceResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<sr.grpc.gen.ReleaseDeviceRequest,
      sr.grpc.gen.ReleaseDeviceResponse> METHOD_RELEASE_DEVICE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "lab.DeviceManager", "ReleaseDevice"),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.ReleaseDeviceRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.ReleaseDeviceResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<sr.grpc.gen.ActionParams,
      sr.grpc.gen.ActionResult> METHOD_ACTION =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "lab.DeviceManager", "Action"),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.ActionParams.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.ActionResult.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<sr.grpc.gen.ObserveDeviceRequest,
      sr.grpc.gen.ObserveDeviceResponse> METHOD_OBSERVE_DEVICE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "lab.DeviceManager", "ObserveDevice"),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.ObserveDeviceRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.ObserveDeviceResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<sr.grpc.gen.UnobserveDeviceRequest,
      sr.grpc.gen.UnobserveDeviceResponse> METHOD_UNOBSERVE_DEVICE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "lab.DeviceManager", "UnobserveDevice"),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.UnobserveDeviceRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.UnobserveDeviceResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DeviceManagerStub newStub(io.grpc.Channel channel) {
    return new DeviceManagerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DeviceManagerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DeviceManagerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static DeviceManagerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DeviceManagerFutureStub(channel);
  }

  /**
   */
  public static abstract class DeviceManagerImplBase implements io.grpc.BindableService {

    /**
     */
    public void listDevices(sr.grpc.gen.ListDevicesRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.DeviceInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LIST_DEVICES, responseObserver);
    }

    /**
     */
    public void controlDevice(sr.grpc.gen.ControlDeviceRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ControlDeviceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CONTROL_DEVICE, responseObserver);
    }

    /**
     */
    public void releaseDevice(sr.grpc.gen.ReleaseDeviceRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ReleaseDeviceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RELEASE_DEVICE, responseObserver);
    }

    /**
     */
    public void action(sr.grpc.gen.ActionParams request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ActionResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ACTION, responseObserver);
    }

    /**
     */
    public void observeDevice(sr.grpc.gen.ObserveDeviceRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ObserveDeviceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_OBSERVE_DEVICE, responseObserver);
    }

    /**
     */
    public void unobserveDevice(sr.grpc.gen.UnobserveDeviceRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.UnobserveDeviceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UNOBSERVE_DEVICE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_LIST_DEVICES,
            asyncServerStreamingCall(
              new MethodHandlers<
                sr.grpc.gen.ListDevicesRequest,
                sr.grpc.gen.DeviceInfo>(
                  this, METHODID_LIST_DEVICES)))
          .addMethod(
            METHOD_CONTROL_DEVICE,
            asyncUnaryCall(
              new MethodHandlers<
                sr.grpc.gen.ControlDeviceRequest,
                sr.grpc.gen.ControlDeviceResponse>(
                  this, METHODID_CONTROL_DEVICE)))
          .addMethod(
            METHOD_RELEASE_DEVICE,
            asyncUnaryCall(
              new MethodHandlers<
                sr.grpc.gen.ReleaseDeviceRequest,
                sr.grpc.gen.ReleaseDeviceResponse>(
                  this, METHODID_RELEASE_DEVICE)))
          .addMethod(
            METHOD_ACTION,
            asyncUnaryCall(
              new MethodHandlers<
                sr.grpc.gen.ActionParams,
                sr.grpc.gen.ActionResult>(
                  this, METHODID_ACTION)))
          .addMethod(
            METHOD_OBSERVE_DEVICE,
            asyncServerStreamingCall(
              new MethodHandlers<
                sr.grpc.gen.ObserveDeviceRequest,
                sr.grpc.gen.ObserveDeviceResponse>(
                  this, METHODID_OBSERVE_DEVICE)))
          .addMethod(
            METHOD_UNOBSERVE_DEVICE,
            asyncUnaryCall(
              new MethodHandlers<
                sr.grpc.gen.UnobserveDeviceRequest,
                sr.grpc.gen.UnobserveDeviceResponse>(
                  this, METHODID_UNOBSERVE_DEVICE)))
          .build();
    }
  }

  /**
   */
  public static final class DeviceManagerStub extends io.grpc.stub.AbstractStub<DeviceManagerStub> {
    private DeviceManagerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DeviceManagerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeviceManagerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DeviceManagerStub(channel, callOptions);
    }

    /**
     */
    public void listDevices(sr.grpc.gen.ListDevicesRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.DeviceInfo> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_LIST_DEVICES, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void controlDevice(sr.grpc.gen.ControlDeviceRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ControlDeviceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CONTROL_DEVICE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void releaseDevice(sr.grpc.gen.ReleaseDeviceRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ReleaseDeviceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RELEASE_DEVICE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void action(sr.grpc.gen.ActionParams request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ActionResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ACTION, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void observeDevice(sr.grpc.gen.ObserveDeviceRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ObserveDeviceResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_OBSERVE_DEVICE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void unobserveDevice(sr.grpc.gen.UnobserveDeviceRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.UnobserveDeviceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UNOBSERVE_DEVICE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DeviceManagerBlockingStub extends io.grpc.stub.AbstractStub<DeviceManagerBlockingStub> {
    private DeviceManagerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DeviceManagerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeviceManagerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DeviceManagerBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<sr.grpc.gen.DeviceInfo> listDevices(
        sr.grpc.gen.ListDevicesRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_LIST_DEVICES, getCallOptions(), request);
    }

    /**
     */
    public sr.grpc.gen.ControlDeviceResponse controlDevice(sr.grpc.gen.ControlDeviceRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CONTROL_DEVICE, getCallOptions(), request);
    }

    /**
     */
    public sr.grpc.gen.ReleaseDeviceResponse releaseDevice(sr.grpc.gen.ReleaseDeviceRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RELEASE_DEVICE, getCallOptions(), request);
    }

    /**
     */
    public sr.grpc.gen.ActionResult action(sr.grpc.gen.ActionParams request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ACTION, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<sr.grpc.gen.ObserveDeviceResponse> observeDevice(
        sr.grpc.gen.ObserveDeviceRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_OBSERVE_DEVICE, getCallOptions(), request);
    }

    /**
     */
    public sr.grpc.gen.UnobserveDeviceResponse unobserveDevice(sr.grpc.gen.UnobserveDeviceRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UNOBSERVE_DEVICE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DeviceManagerFutureStub extends io.grpc.stub.AbstractStub<DeviceManagerFutureStub> {
    private DeviceManagerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DeviceManagerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeviceManagerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DeviceManagerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sr.grpc.gen.ControlDeviceResponse> controlDevice(
        sr.grpc.gen.ControlDeviceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CONTROL_DEVICE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sr.grpc.gen.ReleaseDeviceResponse> releaseDevice(
        sr.grpc.gen.ReleaseDeviceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RELEASE_DEVICE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sr.grpc.gen.ActionResult> action(
        sr.grpc.gen.ActionParams request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ACTION, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sr.grpc.gen.UnobserveDeviceResponse> unobserveDevice(
        sr.grpc.gen.UnobserveDeviceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UNOBSERVE_DEVICE, getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_DEVICES = 0;
  private static final int METHODID_CONTROL_DEVICE = 1;
  private static final int METHODID_RELEASE_DEVICE = 2;
  private static final int METHODID_ACTION = 3;
  private static final int METHODID_OBSERVE_DEVICE = 4;
  private static final int METHODID_UNOBSERVE_DEVICE = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DeviceManagerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DeviceManagerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST_DEVICES:
          serviceImpl.listDevices((sr.grpc.gen.ListDevicesRequest) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.DeviceInfo>) responseObserver);
          break;
        case METHODID_CONTROL_DEVICE:
          serviceImpl.controlDevice((sr.grpc.gen.ControlDeviceRequest) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.ControlDeviceResponse>) responseObserver);
          break;
        case METHODID_RELEASE_DEVICE:
          serviceImpl.releaseDevice((sr.grpc.gen.ReleaseDeviceRequest) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.ReleaseDeviceResponse>) responseObserver);
          break;
        case METHODID_ACTION:
          serviceImpl.action((sr.grpc.gen.ActionParams) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.ActionResult>) responseObserver);
          break;
        case METHODID_OBSERVE_DEVICE:
          serviceImpl.observeDevice((sr.grpc.gen.ObserveDeviceRequest) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.ObserveDeviceResponse>) responseObserver);
          break;
        case METHODID_UNOBSERVE_DEVICE:
          serviceImpl.unobserveDevice((sr.grpc.gen.UnobserveDeviceRequest) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.UnobserveDeviceResponse>) responseObserver);
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

  private static final class DeviceManagerDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return sr.grpc.gen.LabProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DeviceManagerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DeviceManagerDescriptorSupplier())
              .addMethod(METHOD_LIST_DEVICES)
              .addMethod(METHOD_CONTROL_DEVICE)
              .addMethod(METHOD_RELEASE_DEVICE)
              .addMethod(METHOD_ACTION)
              .addMethod(METHOD_OBSERVE_DEVICE)
              .addMethod(METHOD_UNOBSERVE_DEVICE)
              .build();
        }
      }
    }
    return result;
  }
}
