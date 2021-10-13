package ca.ulaval.glo3100.shamir;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.polynomials.Polynomial;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ShamirService {

    private static final Random RANDOM = new Random();

    public static void execute(Args args) {
        switch (args.operation) {
            case ENCRYPT:
                encrypt(args.k, args.n, args.s, args.q);
                break;
            default:
            case DECRYPT:
                decrypt(args.points, args.q);
                break;
        }
    }

    /**
     * @param k Number of coefficient (minimum of keys to decrypt)
     * @param n Number of generated keys
     * @param s First coefficient (c_0)
     * @param q Prime number used to generate coefficients
     */
    public static void encrypt(int k, int n, int s, int q) {
        List<Integer> coefficients = new ArrayList<>();
        coefficients.add(s);

        for (int i = 0; i < k; i++) {
            coefficients.add(generateRandomInt(0, q));
        }

        Polynomial polynomial = new Polynomial(coefficients);

        Logger.logInfo(String.format("Polynomial : %s", polynomial));

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = i + 1;
            points.add(new Point(x, polynomial.getResult(x) % q));
        }

        Logger.logInfo(String.format("Points : %s", joinPoints(points)));
    }

    // TODO : Why do we need q???
    /**
     * @param points Keys to build back the first key (first coefficient)
     * @param q Prime number used to generate coefficients
     */
    public static void decrypt(List<Point> points, int q) {
        int k = points.size();

        // TODO : Rename, this is the list of I
        List<Double> i = new ArrayList<>();

        // Build each I_i(0)
        for (int j = 0; j < k; j++) {
            // TODO : Rename this (sum of multiplications?)
            double result = 1;

            for (int m = 0; m < k; m++) {
               if (m != j) {
                   result *= (double) (points.get(m).x) / (points.get(m).x - points.get(j).x);
               }
            }

            i.add(result);
        }

        // Sum to find L_0 (s)
        double s = 0;
        for (int j = 0; j < k; j++) {
            s += points.get(j).y * i.get(j);
        }

        Logger.logInfo(String.format("S (c_0) : %s", s));
    }

    // TODO : Move
    private static int generateRandomInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    // TODO : Move
    private static String joinPoints(List<Point> points) {
        return points
                .stream()
                .map(point -> String.format("(%s, %s)", point.x, point.y))
                .collect(Collectors.joining(", "));
    }
}
