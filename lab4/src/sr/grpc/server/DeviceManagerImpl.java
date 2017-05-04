package sr.grpc.server;

import io.grpc.stub.StreamObserver;
import sr.grpc.gen.*;
import sr.grpc.gen.DeviceManagerGrpc.DeviceManagerImplBase;
import sr.grpc.gen.UserManagerGrpc.UserManagerImplBase;

class DeviceManagerImpl extends DeviceManagerImplBase {
    @Override
    public void listDevices(ListDevicesRequest request,
                             StreamObserver<DeviceInfo> responseObserver) {
        System.out.println("List devices request received");
        for (Device d : Device.nameMap.values()) {
            System.out.println("     processing device " + d.getName());
            String controller = "no one";
            if (d.getUser() != null)
                controller = d.getUser().getId().toString();
            DeviceInfo deviceInfo = DeviceInfo.newBuilder()
                    .setInfo(d.getName() + " : controlled by " + controller).build();
            responseObserver.onNext(deviceInfo);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void controlDevice(ControlDeviceRequest request,
                              StreamObserver<ControlDeviceResponse> responseObserver) {
        System.out.println("Control device request received");
        ControlDeviceResponse ctrlDevResponse;
        if (!Device.nameMap.containsKey(request.getDeviceName())
                || Device.nameMap.get(request.getDeviceName()).getUser() != null
                || !User.idMap.keySet().contains(request.getUserId())
                || User.idMap.get(request.getUserId()).getDevice() != null
                || User.idMap.get(request.getUserId()).getObservedDevice() != null) {
            ctrlDevResponse = ControlDeviceResponse.newBuilder().setAccepted(false)
                    .setIntruction("").build();
        } else {
            Device device = Device.nameMap.get(request.getDeviceName());
            device.setController(User.idMap.get(request.getUserId()));
            ctrlDevResponse = ControlDeviceResponse.newBuilder().setAccepted(true)
                    .setIntruction(device.getInstruction()).build();
        }

        responseObserver.onNext(ctrlDevResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void observeDevice(ObserveDeviceRequest request,
                              StreamObserver<ObserveDeviceResponse> responseObserver) {
        System.out.println("Observe device request received");
        ObserveDeviceResponse obsrvDevResponse;
        if (!Device.nameMap.containsKey(request.getDeviceName())
                || Device.nameMap.get(request.getDeviceName()).getUser() == null
                || !User.idMap.keySet().contains(request.getUserId())
                || User.idMap.get(request.getUserId()).getDevice() != null
                || User.idMap.get(request.getUserId()).getObservedDevice() != null) {
            obsrvDevResponse = ObserveDeviceResponse.newBuilder().setAccepted(false).build();
            responseObserver.onNext(obsrvDevResponse);
            responseObserver.onCompleted();
        } else {
            Device device = Device.nameMap.get(request.getDeviceName());
            device.addObserver(User.idMap.get(request.getUserId()));
            obsrvDevResponse = ObserveDeviceResponse.newBuilder().setAccepted(true)
                    .setResult(device.getOutput()).build();
            responseObserver.onNext(obsrvDevResponse);
            User.idMap.get(request.getUserId()).setResponseObserver(responseObserver);
        }
    }

    @Override
    public void unobserveDevice(UnobserveDeviceRequest request,
                                StreamObserver<UnobserveDeviceResponse> responseObserver) {
        System.out.println("Unobserve device request received");
        UnobserveDeviceResponse uobsDevResponse;
        if (!User.idMap.containsKey(request.getUserId())
                || User.idMap.get(request.getUserId()).getDevice() != null
                || User.idMap.get(request.getUserId()).getObservedDevice() == null) {
            uobsDevResponse = UnobserveDeviceResponse.newBuilder().setAccepted(false).build();
        } else {
            Device device = User.idMap.get(request.getUserId()).getObservedDevice();
            device.removeObserver(User.idMap.get(request.getUserId()));
            uobsDevResponse = UnobserveDeviceResponse.newBuilder().setAccepted(true).build();
        }

        responseObserver.onNext(uobsDevResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void releaseDevice(ReleaseDeviceRequest request,
                              StreamObserver<ReleaseDeviceResponse> responseObserver) {
        System.out.println("Release device request received");
        ReleaseDeviceResponse rlsDevResponse;
        if (!User.idMap.containsKey(request.getUserId())
            || User.idMap.get(request.getUserId()).getDevice() == null) {
            rlsDevResponse = ReleaseDeviceResponse.newBuilder().setAccepted(false).build();
        } else {
            Device device = User.idMap.get(request.getUserId()).getDevice();
            device.setController(null);
            rlsDevResponse = ReleaseDeviceResponse.newBuilder().setAccepted(true).build();
        }

        responseObserver.onNext(rlsDevResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void action(ActionParams request,
                       StreamObserver<ActionResult> responseObserver) {
        System.out.println("Device action request received");

        if (!User.idMap.containsKey(request.getUserId())
                || User.idMap.get(request.getUserId()).getDevice() == null) {
            ActionResult actionResult = ActionResult.newBuilder().setResult("No associated device found.").build();
            responseObserver.onNext(actionResult);
            responseObserver.onCompleted();
        } else {
            Device device = User.idMap.get(request.getUserId()).getDevice();
            try {
                device.parseAction(request.getParams());
            } catch (IncorrectActionParams e) {
                ActionResult actionResult = ActionResult.newBuilder().setResult("Incorrect parameters.").build();
                responseObserver.onNext(actionResult);
                responseObserver.onCompleted();
                return;
            }

            ActionResult actionResult = ActionResult.newBuilder().setResult(device.getOutput()).build();
            responseObserver.onNext(actionResult);
            responseObserver.onCompleted();

            for (User o : device.getObservers()) {
                if (o.getResponseObserver() != null) {
                    ObserveDeviceResponse obsrvDevResponse = ObserveDeviceResponse.newBuilder().setAccepted(true)
                            .setResult(device.getOutput()).setCommand(request.getParams()).build();
                    o.getResponseObserver().onNext(obsrvDevResponse);
                }
            }
        }
    }
}
