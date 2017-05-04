package sr.grpc.server;

/**
 * Created by zero on 2017-05-04.
 */
public class OrionTelescope extends Device implements Telescope {
    private double a = 15.0;
    private double b = 14.32323232;
    private double c = 123.32;
    private double d = 13222.2333;

    public OrionTelescope(String name) {
        super(name);
    }

    @Override
    public String getInstruction() {
        return "\"dev setAlpha VALUE\" - changes the alpha value to VALUE\n"
                + "\"dev setBeta VALUE\" - changes the beta value to VALUE\n"
                + "\"dev setFrequency VALUE\" - changes the frequency value to VALUE\n"
                + "\"dev setBrightness VALUE\" - changes the brightness value to VALUE";
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
        if (params[0].equalsIgnoreCase("setAlpha"))
            setAlpha(vector);
        else if (params[0].equalsIgnoreCase("setBeta"))
            setBeta(vector);
        else if (params[0].equalsIgnoreCase("setFrequency"))
            setFrequency(vector);
        else if (params[0].equalsIgnoreCase("setBrightness"))
            setBrightness(vector);
        else
            throw new IncorrectActionParams("awwwww");
    }

    @Override
    public void setAlpha(double a) {
        this.a = Math.max(Math.min(a, 20.0f), -20.0f);
    }

    @Override
    public void setBeta(double b) {
        this.b = Math.max(Math.min(b, 30.0f), -30.0f);
    }

    @Override
    public void setFrequency(double f) {
        this.c = Math.max(Math.min(f, 31232.0f), 0.0f);
    }

    @Override
    public void setBrightness(double b) {
        this.d = Math.max(Math.min(b, 12112.0), 0.0f);
    }

    @Override
    public String getOutput() {
        double output = (double)Math.log(this.a) * this.b * this.c + this.d * 200;
        return Double.toString(output);
    }
}
