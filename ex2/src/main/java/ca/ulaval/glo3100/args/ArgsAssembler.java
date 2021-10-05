package ca.ulaval.glo3100.args;

public class ArgsAssembler {

    private static final String msgArg = "-msg";
    private static final String keyArg = "-key";
    private static final String opArg = "-op";
    private static final String modeArg = "-mode";
    private static final String ivArg = "-iv";
    private static final String rArg = "-r";

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
                case msgArg:
                    message = args[i + 1];
                    break;
                case keyArg:
                    key = args[i + 1];
                    break;
                case opArg:
                    operation = Operation.get(args[i + 1]);
                    break;
                case modeArg:
                    mode = Mode.get(args[i + 1]);
                    break;
                case ivArg:
                    iv = args[i + 1];
                    break;
                case rArg:
                    r = Integer.parseInt(args[i + 1]);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid arguments");
            }
        }

        return new Args(message, key, operation, iv, mode, r);
    }
}
