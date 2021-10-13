package ca.ulaval.glo3100.args;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArgsAssembler {

    private static final int DEFAULT_INT_VALUE = -1;
    private static final String E_ARG = "-e";
    private static final String D_ARG = "-d";
    private static final String K_ARG = "-k";
    private static final String N_ARG = "-n";
    private static final String S_ARG = "-s";
    private static final String Q_ARG = "-q";
    private static final String P_ARG = "-p";

    public static Args assemble(String[] args) {
        // Default values
        Operation operation = null;
        int k = DEFAULT_INT_VALUE;
        int n = DEFAULT_INT_VALUE;
        int s = DEFAULT_INT_VALUE;
        int q = DEFAULT_INT_VALUE;
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]){
                case E_ARG:
                    if (operation == Operation.DECRYPT) {
                        throw new IllegalArgumentException("Provide only -e or -d");
                    }
                    operation = Operation.ENCRYPT;
                    break;
                case D_ARG:
                    if (operation == Operation.ENCRYPT) {
                        throw new IllegalArgumentException("Provide only -e or -d");
                    }
                    operation = Operation.DECRYPT;
                    break;
                case K_ARG:
                    k = Integer.parseInt(args[i + 1]);
                    break;
                case N_ARG:
                    n = Integer.parseInt(args[i + 1]);
                    break;
                case S_ARG:
                    s = Integer.parseInt(args[i + 1]);
                    break;
                case Q_ARG:
                    q = Integer.parseInt(args[i + 1]);
                    break;
                case P_ARG:
                    int x = Integer.parseInt(args[i + 1]);
                    int y = Integer.parseInt(args[i + 2]);
                    points.add(new Point(x, y));
                    break;
            }
        }

        if (operation == null) {
            throw new IllegalArgumentException("Provide -e or -d");
        }

        if (q == DEFAULT_INT_VALUE || !isPrime(q)) {
            throw new IllegalArgumentException("Provide -q with a prime number");
        }

        if (operation == Operation.ENCRYPT) {
            if (k == DEFAULT_INT_VALUE || n == DEFAULT_INT_VALUE || s == DEFAULT_INT_VALUE) {
                throw new IllegalArgumentException("To encrypt, you must provide k, n, s and q. Ex. : 'ex3 -e -k  3 -n 5 -s 7 -q 31'");
            }

            if (k > n) {
                throw new IllegalArgumentException("k must be lower or equal to n");
            }
        }

        if (operation == Operation.DECRYPT) {
            if (points.isEmpty()) {
                throw new IllegalArgumentException("To decrypt, you must provide p (points) Ex. : 'ex3 -d -p 3 5 -p 1 16 -p 2 5 -q 31'");
            }
        }

        return new Args(operation, k, n, s, q, points);
    }

    private static boolean isPrime(int number) {
        if (number % 2 == 0) {
            return false;
        }

        for(int i = 3; i*i <= number ; i += 2) {
            if(number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
