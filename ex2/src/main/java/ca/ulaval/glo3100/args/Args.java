package ca.ulaval.glo3100.args;

public class Args {
    public String msg;
    public String key;
    public String op;
    public String iv;
    public Mode mode;
    public int r;

    public Args(String msg, String key, String op, String iv, Mode mode, int r) {
        this.msg = msg;
        this.key = key;
        this.op = op;
        this.iv = iv;
        this.mode = mode;
        this.r = r;
    }
}
