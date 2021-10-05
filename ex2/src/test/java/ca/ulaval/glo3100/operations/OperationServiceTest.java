package ca.ulaval.glo3100.operations;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.args.Mode;
import ca.ulaval.glo3100.args.Operation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationServiceTest {

    private static final String KEY = "10011111";
    private static final String IV = "00111110";
    private static final int R = 5;

    // The following tests simply copy the screenshot given with the exercise.

    @Test
    public void givenEcbEncrypt_whenExecuting_thenEncryptCorrectly() {
        String expectedResult = "01110001 01100001 00100000";
        String message = "111011101111111010111111";
        Args args = new Args(message, KEY, Operation.ENCRYPT, null, Mode.ECB, 0);

        String result = OperationService.execute(args);

        assertEquals(expectedResult, result);
    }

    @Test
    public void givenEcbDecrypt_whenExecuting_thenDecryptCorrectly() {
        String expectedResult = "11101110 11111110 10111111";
        String message = "011100010110000100100000";
        Args args = new Args(message, KEY, Operation.DECRYPT, null, Mode.ECB, 0);

        String result = OperationService.execute(args);

        assertEquals(expectedResult, result);
    }

    @Test
    public void givenCbcEncrypt_whenExecuting_thenEncryptCorrectly() {
        String expectedResult = "00111110 01001111 00101110 00001110";
        String message = "111011101111111010111111";
        Args args = new Args(message, KEY, Operation.ENCRYPT, IV, Mode.CBC, 0);

        String result = OperationService.execute(args);

        assertEquals(expectedResult, result);
    }

    @Test
    public void givenCbcDecrypt_whenExecuting_thenDecryptCorrectly() {
        String expectedResult = "11101110 11111110 10111111";
        String message = "00111110010011110010111000001110";
        Args args = new Args(message, KEY, Operation.DECRYPT, null, Mode.CBC, 0);

        String result = OperationService.execute(args);

        assertEquals(expectedResult, result);
    }

    @Test
    public void givenOfbEncrypt_whenExecuting_thenEncryptCorrectly() {
        String expectedResult = "00111110 01001 11100 01011 01100 0101";
        String message = "111011101111111010111111";
        Args args = new Args(message, KEY, Operation.ENCRYPT, IV, Mode.OFB, R);

        String result = OperationService.execute(args);

        assertEquals(expectedResult, result);
    }

    @Test
    public void givenOfbDecrypt_whenExecuting_thenDecryptCorrectly() {
        String expectedResult = "11101 11011 11111 01011 1111";
        String message = "00111110010011110001011011000101";
        Args args = new Args(message, KEY, Operation.DECRYPT, IV, Mode.OFB, R);

        String result = OperationService.execute(args);

        assertEquals(expectedResult, result);
    }

    @Test
    public void givenCfbEncrypt_whenExecuting_thenEncryptCorrectly() {
        String expectedResult = "00111110 01001 10001 01010 11101 0011";
        String message = "111011101111111010111111";
        Args args = new Args(message, KEY, Operation.ENCRYPT, IV, Mode.CFB, R);

        String result = OperationService.execute(args);

        assertEquals(expectedResult, result);
    }

    @Test
    public void givenCfbDecrypt_whenExecuting_thenDecryptCorrectly() {
        String expectedResult = "11101 11011 11111 01011 1111";
        String message = "00111110010011000101010111010011";
        Args args = new Args(message, KEY, Operation.DECRYPT, IV, Mode.CFB, R);

        String result = OperationService.execute(args);

        assertEquals(expectedResult, result);
    }

    @Test
    public void givenCtrEncrypt_whenExecuting_thenEncryptCorrectly() {
        String expectedResult = "00111110 01001111 01011110 01100000";
        String message = "111011101111111010111111";
        Args args = new Args(message, KEY, Operation.ENCRYPT, IV, Mode.CTR, 0);

        String result = OperationService.execute(args);

        assertEquals(expectedResult, result);
    }

    @Test
    public void givenCtrDecrypt_whenExecuting_thenDecryptCorrectly() {
        String expectedResult = "11101110 11111110 10111111";
        String message = "00111110010011110101111001100000";
        Args args = new Args(message, KEY, Operation.DECRYPT, IV, Mode.CTR, 0);

        String result = OperationService.execute(args);

        assertEquals(expectedResult, result);
    }
}
