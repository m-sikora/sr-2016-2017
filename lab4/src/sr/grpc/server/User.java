package sr.grpc.server;

import io.grpc.stub.StreamObserver;
import sr.grpc.gen.ObserveDeviceResponse;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by zero on 2017-05-03.
 */
public class User {
    public static HashMap<Integer, User> idMap = new HashMap<>();

    public static User createUser() {
        int maxId = 0;
        if (!idMap.keySet().isEmpty())
            maxId = Collections.max(idMap.keySet());
        return new User(maxId + 1);
    }

    private Integer id;
    private Device device;
    private Device observedDevice;
    private StreamObserver<ObserveDeviceResponse> responseObserver;

    public User() {
        this(null);
    }

    public User(Integer id) {
        if (id == null) {
            this.id = null;
            return;
        }

        if (idMap.containsKey(id)) {
            this.id = null;
            return;
        }

        this.id = id;
        idMap.put(id, this);
    }

    public Integer getId() {
        return id;
    }

    public Device getDevice() {
        return device;
    }

    public Device getObservedDevice() {
        return observedDevice;
    }

    public StreamObserver<ObserveDeviceResponse> getResponseObserver() {
        return responseObserver;
    }

    public void setResponseObserver(StreamObserver<ObserveDeviceResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void setObservedDevice(Device observedDevice) { this.observedDevice = observedDevice; }
}
