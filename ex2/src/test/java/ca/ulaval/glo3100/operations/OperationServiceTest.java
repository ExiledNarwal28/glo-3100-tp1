package ca.ulaval.glo3100.operations;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.args.Mode;
import ca.ulaval.glo3100.args.Operation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationServiceTest {

    @Test
    public void givenEcbDecryption_whenExecuting_thenDecrypt() {
        String expectedResult = "01110001 01100001 00100000";
        Args args = new Args("111011101111111010111111", "10011111", Operation.DECRYPT, null, Mode.ECB, 0);

        String result = OperationService.execute(args);

        assertEquals(result, expectedResult);
    }

    // TODO : Add rest of tests
}
