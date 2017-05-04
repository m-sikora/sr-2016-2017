package sr.grpc.server;

/**
 * Created by zero on 2017-05-04.
 */
public class Utils {
    public static boolean isNumeric(String s) {
        try {
            double d = Double.parseDouble(s);
        } catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

class IncorrectActionParams extends Exception {
    public IncorrectActionParams(String msg) {
        super(msg);
    }
}
