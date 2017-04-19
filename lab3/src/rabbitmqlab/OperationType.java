package rabbitmqlab;

/**
 * Created by zero on 2017-04-19.
 */
public enum OperationType {
    OPERATION_ANKLE("ankle"),
    OPERATION_KNEE("knee"),
    OPERATION_ELBOW("elbow");

    public final String name;
    OperationType(String name) {
        this.name = name;
    }

    public static OperationType getByName(String name) {
        if (name.equalsIgnoreCase("ankle"))
            return OPERATION_ANKLE;
        if (name.equalsIgnoreCase("knee"))
            return OPERATION_KNEE;
        return OPERATION_ELBOW;
    }
}
