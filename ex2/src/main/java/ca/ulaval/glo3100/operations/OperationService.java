package ca.ulaval.glo3100.operations;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.args.Operation;
import ca.ulaval.glo3100.utils.Encryption;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo3100.utils.ByteUtils.*;
import static ca.ulaval.glo3100.utils.EncryptionUtils.applyEncryption;
import static ca.ulaval.glo3100.utils.StringUtils.concatStrings;
import static ca.ulaval.glo3100.utils.StringUtils.getSubstrings;

public class OperationService {

    private static final int SUBSTRING_LENGTH_FOR_ECB = 8;
    private static final int SUBSTRING_LENGTH_FOR_CBC = 8;
    // TODO : Maybe we should move XOR to EncryptionUtils
    private static final Encryption<Long> XOR = ((firstBytes, secondBytes) -> firstBytes ^ secondBytes);

    public static String execute(Args args) {
       return startMode(args);
    }

    private static String startMode(Args args) {
        switch (args.mode){
            case ECB:
                return ecb(args.message, args.key);
            case CBC:
                return cbc(args.message, args.key, args.iv, args.operation);
            case CTR:
                return ctr();
            case OFB:
                return ofb();
            case CFB:
                return cfb();
            default:
                return "";
        }
    }

    /**
     * Encrypts or decrypts message using ECB operation
     * ECB has the same encryption or decryption : a simple XOR operation on each byte
     * @param message String to encrypt or decrypt
     * @param key Key use for ECB operation
     * @return Encrypted or decrypted message
     */
    private static String ecb(String message, String key) {
        List<String> substrings = getSubstrings(message, SUBSTRING_LENGTH_FOR_ECB);
        List<Long> substringsBytes = getBytes(substrings);
        long keyBytes = getByte(key);
        List<Long> encryptedBytes = applyEncryption(substringsBytes, keyBytes, XOR);
        List<String> encryptedMessageSubstrings = getTexts(encryptedBytes);

        return concatStrings(encryptedMessageSubstrings);
    }

    private static String cbc(String message, String key, String iv, Operation operation) {
        switch (operation) {
            case ENCRYPT:
                return encryptCbc(message, key, iv);
            default:
            case DECRYPT:
                return decryptCbc(message, key);
        }
    }

    private static String encryptCbc(String message, String key, String iv) {
        List<String> substrings = getSubstrings(message, SUBSTRING_LENGTH_FOR_CBC);
        List<Long> substringsBytes = getBytes(substrings);
        long keyBytes = getByte(key);

        List<Long> encryptedBytes = new ArrayList<>();
        encryptedBytes.add(getByte(iv));

        if (substrings.size() > 1) {
            for (Long substringsByte : substringsBytes) {
                long lastEncryptedBytes = encryptedBytes.get(encryptedBytes.size() - 1);
                long partiallyEncryptedBytes = applyEncryption(substringsByte, lastEncryptedBytes, XOR);
                encryptedBytes.add(applyEncryption(partiallyEncryptedBytes, keyBytes, XOR));
            }
        }

        List<String> encryptedMessageSubstrings = getTexts(encryptedBytes);

        return concatStrings(encryptedMessageSubstrings);
    }

    // TODO : See if encrypt and decrypt can be partially merged
    private static String decryptCbc(String message, String key) {
        List<String> substrings = getSubstrings(message, SUBSTRING_LENGTH_FOR_CBC);
        List<Long> substringsBytes = getBytes(substrings);
        long keyBytes = getByte(key);

        // TODO : If substring.size < 1

        List<Long> decryptedBytes = new ArrayList<>();
        for (int i = 1; i < substringsBytes.size(); i++) {
            long partiallyDecryptedBytes = applyEncryption(substringsBytes.get(i), keyBytes, XOR);
            long decryptedSubstringBytes = applyEncryption(substringsBytes.get(i - 1), partiallyDecryptedBytes, XOR);
            decryptedBytes.add(decryptedSubstringBytes);
        }

        List<String> decryptedMessageSubstrings = getTexts(decryptedBytes);

        return concatStrings(decryptedMessageSubstrings);
    }

    // TODO : Complete CFB
    private static String cfb() {
        return "";
    }

    // TODO : Complete OFB
    private static String ofb() {
        return "";
    }

    // TODO : Complete CTR
    private static String ctr() {
        return "";
    }
}
