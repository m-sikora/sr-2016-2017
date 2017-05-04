package sr.grpc.server;

/**
 * Created by zero on 2017-05-04.
 */
public interface Telescope {
    void setAlpha(double a);
    void setBeta(double b);
    void setFrequency(double f);
    void setBrightness(double b);
}
