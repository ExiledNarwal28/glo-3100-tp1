package ca.ulaval.glo3100;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.args.ArgsAssembler;
import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.shamir.ShamirService;

public class Main {

    public static void main(String[] args) {
        // TODO : Remove this
        Logger.isDebugging = true;

        Args assembledArgs = ArgsAssembler.assemble(args);

        String result = ShamirService.execute(assembledArgs);

        Logger.logInfo(String.format("%s : %s", assembledArgs.operation.toString(), result));
    }
}
