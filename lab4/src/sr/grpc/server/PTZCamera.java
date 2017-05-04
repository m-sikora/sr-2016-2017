package sr.grpc.server;

/**
 * Created by zero on 2017-05-03.
 */
public interface PTZCamera {
    void pan(double vector);
    void tilt(double vector);
    void zoom(double vector);
}
