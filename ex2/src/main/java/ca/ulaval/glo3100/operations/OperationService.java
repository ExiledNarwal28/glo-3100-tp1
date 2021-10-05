package ca.ulaval.glo3100.operations;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.utils.Operation;

import java.util.List;

import static ca.ulaval.glo3100.utils.ByteUtils.*;
import static ca.ulaval.glo3100.utils.OperationUtils.applyKey;
import static ca.ulaval.glo3100.utils.StringUtils.concatStrings;
import static ca.ulaval.glo3100.utils.StringUtils.getSubstrings;

public class OperationService {

    private static final int SUBSTRING_LENGTH_FOR_ECB = 8;
    private static final Operation<Long> XOR = ((firstBytes, secondBytes) -> firstBytes ^ secondBytes);

    public static String execute(Args args) {
       return startMode(args);
    }

    private static String startMode(Args args) {
        switch (args.mode){
            case ECB:
                return ecb(args.message, args.key);
            case CBC:
                return cbc();
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
        List<Long> encryptedBytes = applyKey(substringsBytes, keyBytes, XOR);
        List<String> encryptedMessageSubstrings = getTexts(encryptedBytes);

        return concatStrings(encryptedMessageSubstrings);
    }

    // TODO : Complete CBC
    private static String cbc() {
        return "";
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
