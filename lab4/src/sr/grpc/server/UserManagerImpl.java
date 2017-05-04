package sr.grpc.server;

import sr.grpc.gen.RegistrationResult;
import sr.grpc.gen.RegistrationTicket;
import sr.grpc.gen.UserManagerGrpc.UserManagerImplBase;

/**
 * Created by zero on 2017-05-03.
 */
public class UserManagerImpl extends UserManagerImplBase {
    @Override
    public void registerUser(RegistrationTicket request,
                             io.grpc.stub.StreamObserver<RegistrationResult> responseObserver) {
        System.out.println("User registration request received");
        User user = User.createUser();
        RegistrationResult result = RegistrationResult.newBuilder().setId(user.getId()).build();
        try { Thread.sleep(1000); } catch(java.lang.InterruptedException ex) { }
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
