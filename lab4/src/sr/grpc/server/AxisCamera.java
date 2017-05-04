package sr.grpc.server;

/**
 * Created by zero on 2017-05-03.
 */
public class AxisCamera extends Device implements PTZCamera {
    private double x = 45.0f;
    private double y = 45.0f;
    private double z = 2.0f;

    public AxisCamera(String name) {
        super(name);
    }

    @Override
    public void pan(double vector) {
        this.x = Math.max(Math.min(vector + this.x, 180.0f), -180.0f);
    }

    @Override
    public void tilt(double vector) {
        this.y = Math.max(Math.min(vector + this.y, 180.0f), -180.0f);
    }

    @Override
    public void zoom(double vector) {
        this.z = Math.max(Math.min(vector + this.z, 123.0f), 0.0f);
    }

    @Override
    public String getOutput() {
        double output = (double)Math.log(this.x) * this.y * this.y + this.z;
        return Double.toString(output);
    }

    @Override
    public String getInstruction() {
        return "\"dev pan VECTOR\" - changes the pan value by VECTOR\n"
                + "\"dev tilt VECTOR\" - changes the tilt value by VECTOR\n"
                + "\"dev zoom VECTOR\" - changes the zoom value by VECTOR";
    }

    @Override
    public void parseAction(String actionParams) throws IncorrectActionParams {
        String[] params = actionParams.split(" ");
        if (params.length != 2)
            throw new IncorrectActionParams("aww");
        if (!Utils.isNumeric(params[1])) {
            throw new IncorrectActionParams("awww");
        }
        double vector = Double.parseDouble(params[1]);
        if (params[0].equalsIgnoreCase("pan")) 
            pan(vector);
        else if (params[0].equalsIgnoreCase("tilt"))
            tilt(vector);
        else if (params[0].equalsIgnoreCase("zoom"))
            zoom(vector);
        else
            throw new IncorrectActionParams("awwwww");
    }
}
