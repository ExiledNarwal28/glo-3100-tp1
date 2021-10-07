package ca.ulaval.glo3100.operations;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.args.Operation;
import ca.ulaval.glo3100.console.Logger;
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
                return ofb();
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

        // TODO : List probably not needed
        List<Long> iBytes = new ArrayList<>();
        iBytes.add(getByte(iv));

        // TODO : List probably not needed
        List<Long> oBytes = new ArrayList<>();
        oBytes.add(applyEncryption(iBytes.get(0), keyByte, XOR));

        String oByte = getText(oBytes.get(0));
        String lString = getSubstring(oByte, 0, r);

        if (operation == Operation.DECRYPT) {
            substringsBytes.remove(0);
        }

        List<Long> foundBytes = new ArrayList<>();

        for (String substring : substrings) {
            long foundByte = applyEncryption(getByte(substring), getByte(lString), XOR);

            long lastI = iBytes.get(iBytes.size() - 1);
            // TODO : Not sure
            long newI = (((long) (2 ^ r) * lastI) + foundByte) % (2^(substrings.size()));
            iBytes.add(newI);

            oByte = getText(oBytes.get(0));
            lString = getSubstring(oByte, 0, r);
        }

        if (operation == Operation.ENCRYPT) {
            foundBytes.add(0, getByte(iv));
        }

        List<String> foundMessageSubstrings = getTexts(foundBytes);

        return concatStrings(foundMessageSubstrings);
    }

    // TODO : Complete OFB
    private static String ofb() {
        return "";
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
