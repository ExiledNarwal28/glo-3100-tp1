package ca.ulaval.glo3100.operations;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.args.Operation;
import ca.ulaval.glo3100.utils.Encryption;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo3100.utils.ByteUtils.*;
import static ca.ulaval.glo3100.utils.EncryptionUtils.applyEncryption;
import static ca.ulaval.glo3100.utils.StringUtils.*;

public class OperationService {

    private static final int SUBSTRING_LENGTH_FOR_ECB = 8;
    private static final int SUBSTRING_LENGTH_FOR_CBC = 8;
    private static final int SUBSTRING_LENGTH_FOR_CTR = 8;
    private static final int FIRST_SUBSTRING_LENGTH_FOR_CFB = 8;
    private static final int SUBSTRING_LENGTH_FOR_CFB = 5;
    private static final int FIRST_SUBSTRING_LENGTH_FOR_OFB = 8;
    private static final int SUBSTRING_LENGTH_FOR_OFB = 5;

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
                return ctr(args.message, args.key, args.iv, args.operation);
            case OFB:
                return ofb(args.message, args.key, args.iv, args.r, args.operation);
            case CFB:
                return cfb(args.message, args.key, args.iv, args.r, args.operation);
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
        long keyByte = getByte(key);
        List<Long> encryptedBytes = applyEncryption(substringsBytes, keyByte, XOR);
        List<String> encryptedMessageSubstrings = getTexts(encryptedBytes);

        return concatStrings(encryptedMessageSubstrings);
    }

    /**
     * Encrypts or decrypts message using CBC operation
     * @param message String to encrypt or decrypt
     * @param key Key use for CBC operation
     * @param iv first encrypted byte, used for encryption
     * @return Encrypted or decrypted message
     */
    private static String cbc(String message, String key, String iv, Operation operation) {
        List<String> substrings = getSubstrings(message, SUBSTRING_LENGTH_FOR_CBC);
        List<Long> substringsBytes = getBytes(substrings);
        long keyByte = getByte(key);

        List<Long> foundBytes = new ArrayList<>();

        if (operation == Operation.ENCRYPT) {
            foundBytes.add(getByte(iv));

            if (substrings.size() > 1) {
                for (Long substringsByte : substringsBytes) {
                    long lastEncryptedBytes = foundBytes.get(foundBytes.size() - 1);
                    long partiallyEncryptedBytes = applyEncryption(substringsByte, lastEncryptedBytes, XOR);
                    foundBytes.add(applyEncryption(partiallyEncryptedBytes, keyByte, XOR));
                }
            }
        } else {
            for (int i = 1; i < substringsBytes.size(); i++) {
                long partiallyDecryptedBytes = applyEncryption(substringsBytes.get(i), keyByte, XOR);
                long decryptedSubstringBytes = applyEncryption(substringsBytes.get(i - 1), partiallyDecryptedBytes, XOR);
                foundBytes.add(decryptedSubstringBytes);
            }
        }

        List<String> foundMessageSubstrings = getTexts(foundBytes);

        return concatStrings(foundMessageSubstrings);
    }

    // TODO : Fix CFB
    // TODO : Add javadocs
    private static String cfb(String message, String key, String iv, int r, Operation operation) {
        List<String> substrings = operation == Operation.ENCRYPT
                ? getSubstrings(message, SUBSTRING_LENGTH_FOR_CFB)
                : getSubstrings(message, SUBSTRING_LENGTH_FOR_CFB, FIRST_SUBSTRING_LENGTH_FOR_CFB);
        List<Long> substringsBytes = getBytes(substrings);
        long keyByte = getByte(key);
        long iByte = getByte(iv);

        if (operation == Operation.DECRYPT) {
            substringsBytes.remove(0);
        }

        List<Long> foundBytes = new ArrayList<>();

        for (long substringsByte : substringsBytes) {
            long oByte = applyEncryption(iByte, keyByte, XOR);
            String o = getText(oByte);
            String l = getSubstring(o, 0, r);
            long lByte = getByte(l);

            long foundByte = applyEncryption(substringsByte, lByte, XOR);
            foundBytes.add(foundByte);

            // TODO : This might be wrong, since the first byte is encrypted/decrypted correctly
            iByte = (long) ((((long) Math.pow(2, r) * iByte) + foundByte) % Math.pow(2, substringsBytes.size()));
        }

        List<String> foundMessageSubstrings = new ArrayList<>();

        switch (operation) {
            case ENCRYPT:
                foundMessageSubstrings.add(iv);
                foundMessageSubstrings.addAll(getTexts(foundBytes, r));
                break;
            default:
            case DECRYPT:
                foundMessageSubstrings = getTexts(foundBytes, r);
                break;
        }

        return concatStrings(foundMessageSubstrings);
    }

    // TODO : Cleanup OFB
    // TODO : Add javadocs
    private static String ofb(String message, String key, String iv, int r, Operation operation) {
        List<String> substrings = operation == Operation.ENCRYPT
                ? getSubstrings(message, SUBSTRING_LENGTH_FOR_OFB)
                : getSubstrings(message, SUBSTRING_LENGTH_FOR_OFB, FIRST_SUBSTRING_LENGTH_FOR_OFB);
        List<Long> substringsBytes = getBytes(substrings);
        long keyByte = getByte(key);
        long oByte = getByte(iv);

        if (operation == Operation.DECRYPT) {
            substringsBytes.remove(0);
        }

        List<Long> foundBytes = new ArrayList<>();
        // Keeping the last L to build back message
        String l = "";

        for (int i = 0; i < substringsBytes.size(); i++) {
            oByte = applyEncryption(oByte, keyByte, XOR);
            String o = getText(oByte);
            // TODO : Last byte of decrypt does not work
            l = i == substringsBytes.size() - 1
                    ? getSubstring(o, 0, Math.min(r, Long.toBinaryString(substringsBytes.get(i)).length()))
                    : getSubstring(o, 0, r);
            long lByte = getByte(l);

            long foundByte = applyEncryption(substringsBytes.get(i), lByte, XOR);
            foundBytes.add(foundByte);
        }

        List<String> foundMessageSubstrings = new ArrayList<>();

        switch (operation) {
            case ENCRYPT:
                foundMessageSubstrings.add(iv);
                for (int i = 0; i < foundBytes.size() - 1; i++) {
                    foundMessageSubstrings.add(getText(foundBytes.get(i), r));
                }
                foundMessageSubstrings.add(getText(foundBytes.get(foundBytes.size() - 1), Math.min(r, l.length())));
                break;
            default:
            case DECRYPT:
                for (int i = 0; i < foundBytes.size() - 1; i++) {
                    foundMessageSubstrings.add(getText(foundBytes.get(i), r));
                }
                foundMessageSubstrings.add(getText(foundBytes.get(foundBytes.size() - 1), Math.min(r, l.length())));
                break;
        }

        return concatStrings(foundMessageSubstrings);
    }

    /**
     * Encrypts or decrypts message using CTR operation
     * @param message String to encrypt or decrypt
     * @param key Key use for CTR operation
     * @param iv first counter value
     * @return Encrypted or decrypted message
     */
    private static String ctr(String message, String key, String iv, Operation operation) {
        List<String> substrings = getSubstrings(message, SUBSTRING_LENGTH_FOR_CTR);
        List<Long> substringsBytes = getBytes(substrings);
        long keyByte = getByte(key);

        if (operation == Operation.DECRYPT) {
            substringsBytes.remove(0);
        }

        List<Long> counters = new ArrayList<>();
        long counter = getByte(iv);
        counters.add(counter);

        List<Long> foundBytes = new ArrayList<>();

        for (long substringsByte : substringsBytes) {
            long encryptedCounter = applyEncryption(counter, keyByte, XOR);
            long foundByte = applyEncryption(encryptedCounter, substringsByte, XOR);

            foundBytes.add(foundByte);

            long lastCounter = counters.get(counters.size() - 1);
            counter = lastCounter + 1L;
            counters.add(counter);
        }

        if (operation == Operation.ENCRYPT) {
            foundBytes.add(0, counters.get(0));
        }

        List<String> foundMessageSubstrings = getTexts(foundBytes);

        return concatStrings(foundMessageSubstrings);
    }
}
