package ca.ulaval.glo3100.args;

public class Args {
    public String message;
    public String key;
    public Operation operation;
    public String iv;
    public Mode mode;
    public int r;

    public Args(String message, String key, Operation operation, String iv, Mode mode, int r) {
        this.message = message;
        this.key = key;
        this.operation = operation;
        this.iv = iv;
        this.mode = mode;
        this.r = r;
    }
}
