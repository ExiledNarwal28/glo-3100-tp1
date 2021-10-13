package ca.ulaval.glo3100.args;

import java.awt.*;
import java.util.List;

public class Args {
    public Operation operation;
    public int k;
    public int n;
    public int s;
    public int q;
    public List<Point> points;

    public Args(Operation operation, int k, int n, int s, int q, List<Point> points) {
        this.operation = operation;
        this.k = k;
        this.n = n;
        this.s = s;
        this.q = q;
        this.points = points;
    }
}
