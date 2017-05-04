package sr.grpc.server;

/**
 * Created by zero on 2017-05-03.
 */
public class PelcoCamera extends Device implements PTZCamera {
    private double x = 145.0f;
    private double y = 145.0f;
    private double z = 12.0f;

    public PelcoCamera(String name) {
        super(name);
    }

    @Override
    public void pan(double vector) {
        this.x = Math.max(Math.min(vector + this.x, 90.0f), -90.0f);
    }

    @Override
    public void tilt(double vector) {
        this.y = Math.max(Math.min(vector + this.y, 90.0f), -90.0f);
    }

    @Override
    public void zoom(double vector) {
        this.z = Math.max(Math.min(vector + this.z, 100.0f), 0.0f);
    }

    @Override
    public String getOutput() {
        double output = (double)Math.log(10 * this.x) * this.y * this.y + this.z;
        return Double.toString(output);
    }

    @Override
    public String getInstruction() {
        return "\"dev move_pan VECTOR\" - changes the pan value by VECTOR\n"
                + "\"dev move_tilt VECTOR\" - changes the tilt value by VECTOR\n"
                + "\"dev move_zoom VECTOR\" - changes the zoom value by VECTOR";
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
        if (params[0].equalsIgnoreCase("move_pan"))
            pan(vector);
        else if (params[0].equalsIgnoreCase("move_tilt"))
            tilt(vector);
        else if (params[0].equalsIgnoreCase("move_zoom"))
            zoom(vector);
        else
            throw new IncorrectActionParams("awwwww");
    }
}
