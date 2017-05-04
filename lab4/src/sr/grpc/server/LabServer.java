package sr.grpc.server;

import java.io.IOException;
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class LabServer {
    private static final Logger logger = Logger.getLogger(LabServer.class.getName());

    private int port = 50051;
    private Server server;

    private void start() throws IOException
    {
        server = ServerBuilder.forPort(port)
                .addService(new UserManagerImpl())
                .addService(new DeviceManagerImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                LabServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final LabServer server = new LabServer();
        server.start();
        addSampleDevices();
        server.blockUntilShutdown();
    }

    public static void addSampleDevices() {
        AxisCamera a1 = new AxisCamera("axis-camera-01");
        AxisCamera a2 = new AxisCamera("axis-camera-02");
        BoschCamera b1 = new BoschCamera("bosch-camera-01");
        PelcoCamera p1 = new PelcoCamera("pelco-camera-01");
        PelcoCamera p2 = new PelcoCamera("pelco-camera-02");
        PelcoCamera p3 = new PelcoCamera("pelco-camera-03");
        MessierTelescope m1 = new MessierTelescope("messier-telescope-01");
        MessierTelescope m2 = new MessierTelescope("messier-telescope-02");
        OrionTelescope o1 = new OrionTelescope("orion-telescope-01");
    }


}
