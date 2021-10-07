package ca.ulaval.glo3100.operations;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.args.Operation;
import ca.ulaval.glo3100.console.Logger;
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
    private static final int SUBSTRING_LENGTH_FOR_CTR = 8;
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

    // TODO : Complete CFB
    private static String cfb() {
        // I_0 = IV
        // O_0 = applyEncryption(I_0, keyByte)
        // L_0 = r left bits of O_0

        // if mode is decrypt
        //   remove first of substringBytes
        // end if

        // for each substringBytes
        //   int i = 0;
        //   c_i =
        // end for

        // if mode is encrypt
        //   add iv to start of substringBytes
        // end if

        return "";
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
