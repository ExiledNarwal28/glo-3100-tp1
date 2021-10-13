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
            default:
            case DECRYPT:
                decrypt(args.points, args.q);
        }
    }

    // TODO : Finish encrypt
    // TODO : Add javadocs
    public static void encrypt(int k, int n, int s, int q) {
        List<Integer> coefficients = new ArrayList<>();
        coefficients.add(s);

        for (int i = 0; i < k; i++) {
            coefficients.add(generateRandomInt(0, k));
        }

        Polynomial polynomial = new Polynomial(coefficients);

        Logger.logInfo(String.format("Polynomial : %s", polynomial));

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = i + 1;
            points.add(new Point(x, polynomial.getResult(x, q)));
        }

        Logger.logInfo(String.format("Points : %s", joinPoints(points)));
    }

    // TODO : Finish decrypt
    // TODO : Add javadocs
    public static void decrypt(List<Point> points, int q) {
        // Empty for now!
    }

    // TODO : Move
    private static int generateRandomInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    private static String joinPoints(List<Point> points) {
        return points
                .stream()
                .map(point -> String.format("(%s, %s)", point.x, point.y))
                .collect(Collectors.joining(", "));
    }
}
