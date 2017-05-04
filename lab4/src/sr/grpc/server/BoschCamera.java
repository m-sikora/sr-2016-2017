package sr.grpc.server;

/**
 * Created by zero on 2017-05-03.
 */
public class BoschCamera extends Device implements PTZCamera {
    private double x = 41.0f;
    private double y = 41.0f;
    private double z = 21.0f;

    public BoschCamera(String name) {
        super(name);
    }

    @Override
    public void pan(double vector) {
        this.x = Math.max(Math.min(vector + this.x, 361.0f), 1.0f);
    }

    @Override
    public void tilt(double vector) {
        this.y = Math.max(Math.min(vector + this.y, 90.0f), -90.0f);
    }

    @Override
    public void zoom(double vector) {
        this.z = Math.max(Math.min(vector + this.z, 113.0f), 0.0f);
    }

    @Override
    public String getOutput() {
        double output = (double)Math.log(this.x) * this.y * this.y + this.z * 200;
        return Double.toString(output);
    }

    @Override
    public String getInstruction() {
        return "\"dev p VECTOR\" - changes the pan value by VECTOR\n"
                + "\"dev t VECTOR\" - changes the tilt value by VECTOR\n"
                + "\"dev z VECTOR\" - changes the zoom value by VECTOR";
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
        if (params[0].equalsIgnoreCase("p"))
            pan(vector);
        else if (params[0].equalsIgnoreCase("t"))
            tilt(vector);
        else if (params[0].equalsIgnoreCase("z"))
            zoom(vector);
        else
            throw new IncorrectActionParams("awwwww");
    }
}
