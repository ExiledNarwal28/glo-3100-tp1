package ca.ulaval.glo3100.args;

public class ArgsAssembler {

    private static final String MSG_ARG = "-msg";
    private static final String KEY_ARG = "-key";
    private static final String OP_ARG = "-op";
    private static final String MODE_ARG = "-mode";
    private static final String IV_ARG = "-iv";
    private static final String R_ARG = "-r";

    public static Args assemble(String[] args) {
        // Default values
        String message = null;
        String key = null;
        Operation operation = null;
        String iv = null;
        Mode mode = null;
        int r = 0;

        for (int i = 0; i < args.length; i = i + 2) {
            switch (args[i]){
                case MSG_ARG:
                    message = args[i + 1];
                    break;
                case KEY_ARG:
                    key = args[i + 1];
                    break;
                case OP_ARG:
                    operation = Operation.get(args[i + 1]);
                    break;
                case MODE_ARG:
                    mode = Mode.get(args[i + 1]);
                    break;
                case IV_ARG:
                    iv = args[i + 1];
                    break;
                case R_ARG:
                    r = Integer.parseInt(args[i + 1]);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid arguments");
            }
        }

        return new Args(message, key, operation, iv, mode, r);
    }
}
