package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.console.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static ca.ulaval.glo3100.utils.ByteUtils.getText;

public class OperationUtils {

    public static List<Long> applyKey(List<Long> textsBytes, long keyBytes, Operation<Long> operation) {
        return textsBytes
                .stream()
                .map(textBytes -> applyKey(textBytes, keyBytes, operation))
                .collect(Collectors.toList());
    }

    public static long applyKey(long textBytes, long keyBytes, Operation<Long> operation) {
        Logger.logDebug(String.format("Applying key (%s) to text : %s", getText(keyBytes), getText(textBytes)));

        long encryptedText = operation.operate(textBytes, keyBytes);

        Logger.logDebug(String.format("  -> : %s", getText(encryptedText)));

        return encryptedText;
    }
}
