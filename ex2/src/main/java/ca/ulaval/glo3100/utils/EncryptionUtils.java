package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.console.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static ca.ulaval.glo3100.utils.ByteUtils.getText;

public class EncryptionUtils {

    public static List<Long> applyEncryption(List<Long> textsBytes, long keyBytes, Encryption<Long> encryption) {
        return textsBytes
                .stream()
                .map(textBytes -> applyEncryption(textBytes, keyBytes, encryption))
                .collect(Collectors.toList());
    }

    public static long applyEncryption(long textBytes, long keyBytes, Encryption<Long> encryption) {
        Logger.logDebug(String.format("Applying key (%s) to text : %s", getText(keyBytes), getText(textBytes)));

        long encryptedText = encryption.encrypt(textBytes, keyBytes);

        Logger.logDebug(String.format("  -> : %s", getText(encryptedText)));

        return encryptedText;
    }
}
