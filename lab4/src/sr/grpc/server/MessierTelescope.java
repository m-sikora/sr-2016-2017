package sr.grpc.server;

/**
 * Created by zero on 2017-05-04.
 */
public class MessierTelescope extends Device implements Telescope {
    private double a = 5.0;
    private double b = 4.32323232;
    private double c = 23.32;
    private double d = 3222.2333;

    public MessierTelescope(String name) {
        super(name);
    }

    @Override
    public String getInstruction() {
        return "\"dev alpha VALUE\" - changes the alpha value to VALUE\n"
                + "\"dev beta VALUE\" - changes the beta value to VALUE\n"
                + "\"dev freq VALUE\" - changes the frequency value to VALUE\n"
                + "\"dev bright VALUE\" - changes the brightness value to VALUE";
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
        if (params[0].equalsIgnoreCase("alpha"))
            setAlpha(vector);
        else if (params[0].equalsIgnoreCase("beta"))
            setBeta(vector);
        else if (params[0].equalsIgnoreCase("freq"))
            setFrequency(vector);
        else if (params[0].equalsIgnoreCase("bright"))
            setBrightness(vector);
        else
            throw new IncorrectActionParams("awwwww");
    }

    @Override
    public void setAlpha(double a) {
        this.a = Math.max(Math.min(a, 90.0f), -90.0f);
    }

    @Override
    public void setBeta(double b) {
        this.b = Math.max(Math.min(b, 90.0f), -90.0f);
    }

    @Override
    public void setFrequency(double f) {
        this.c = Math.max(Math.min(f, 323232.0f), 0.0f);
    }

    @Override
    public void setBrightness(double b) {
        this.d = Math.max(Math.min(b, 1212.0), 0.0f);
    }

    @Override
    public String getOutput() {
        double output = (double)Math.log(this.a) * this.b * this.c + this.d * 200;
        return Double.toString(output);
    }
}
