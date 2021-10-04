package ca.ulaval.glo3100;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.args.ArgsAssembler;
import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.operations.OperationService;

public class Main {

    public static void main(String[] args) {
        Args assembledArgs = ArgsAssembler.assemble(args);

        String result = OperationService.execute(assembledArgs);

        Logger.log(String.format("%s, result is : %s", assembledArgs.mode.toString(), result));
    }
}
