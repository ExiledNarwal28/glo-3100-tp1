package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.console.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static ca.ulaval.glo3100.utils.ByteUtils.getText;

public class EncryptionUtils {

    private static final Encryption<Long> XOR = ((firstBytes, secondBytes) -> firstBytes ^ secondBytes);

    public static List<Long> applyEncryption(List<Long> textsBytes, long keyBytes) {
        return textsBytes
                .stream()
                .map(textBytes -> applyEncryption(textBytes, keyBytes))
                .collect(Collectors.toList());
    }

    public static long applyEncryption(long textByte, long keyByte) {
        Logger.logDebug(String.format("Applying key (%s) to text : %s", getText(keyByte), getText(textByte)));

        long encryptedText = XOR.encrypt(textByte, keyByte);

        Logger.logDebug(String.format("  -> : %s", getText(encryptedText)));

        return encryptedText;
    }
}
