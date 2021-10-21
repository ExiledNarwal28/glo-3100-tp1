package ca.ulaval.glo3100.args;

import ca.ulaval.glo3100.console.Logger;

public class ArgsAssembler {

    private static final String MSG_ARG = "-msg";
    private static final String KEY_ARG = "-key";
    private static final String OP_ARG = "-op";
    private static final String MODE_ARG = "-mode";
    private static final String IV_ARG = "-iv";
    private static final String R_ARG = "-r";
    private static final String DEBUG_ARG = "-debug";
    private static final int BYTE_LENGTH = 8;

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
                case DEBUG_ARG:
                    Logger.isDebugging = true;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid arguments");
            }
        }

        if (operation == null) {
            throw new IllegalArgumentException("Operation must be specified with -op (enc or dec)");
        }

        if (mode == null) {
            throw new IllegalArgumentException("Mode must be specified with -mode (ECB, CBC, CTR, OFB or CFB)");
        }

        if (key == null || key.length() != BYTE_LENGTH) {
            throw new IllegalArgumentException("Key of length 8 must be specified with -k");
        }

        // Validation specific to each mode
        switch(mode) {
            case CBC:
                if (operation == Operation.ENCRYPT && (iv == null || iv.length() != BYTE_LENGTH)) {
                    throw new IllegalArgumentException("IV of length 8 must be specified with -iv");
                }
                break;
            case OFB:
            case CFB:
                if (iv == null || iv.length() != BYTE_LENGTH) {
                    throw new IllegalArgumentException("IV of length 8 must be specified with -iv");
                }
                if (r == 0) {
                    throw new IllegalArgumentException("R must be specified with -r");
                }
                break;
            case CTR:
                if (iv == null || iv.length() != BYTE_LENGTH) {
                    throw new IllegalArgumentException("IV of length 8 must be specified with -iv");
                }
                break;
        }

        return new Args(message, key, operation, iv, mode, r);
    }
}
