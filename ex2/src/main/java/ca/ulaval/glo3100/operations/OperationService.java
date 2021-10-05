package ca.ulaval.glo3100.operations;

import ca.ulaval.glo3100.args.Args;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OperationService {

    public static String execute(Args args) {
       return startMode(args);
    }

    private static String startMode(Args args) {
        String result = "";

        switch (args.mode){
            case ECB:
                result = ecb(args.message, args.key);
                break;
            case CBC:
                result = cbc();
                break;
            case CTR:
                result = ctr();
                break;
            case OFB:
                result = ofb();
                break;
            case CFB:
                result = cfb();
                break;
        }

        return result;
    }

    // TODO : Move to specialized class
    private static final Operation<Long> XOR = ((firstBytes, secondBytes) -> firstBytes & secondBytes);

    // TODO : Something doesn't work with bytes conversion / XOR
    private static String ecb(String message, String key) {
        // TODO : Opposite for decryption
        // TODO : Move 8 to static final
        List<String> substrings = getSubstrings(message, 8);
        List<Long> substringsBytes = getBytes(substrings);
        long keyBytes = getByte(key);
        List<Long> encryptedBytes = applyKey(substringsBytes, keyBytes, XOR);
        List<String> encryptedMessageSubtrings = getTexts(encryptedBytes);
        String encryptedMessage = concatSubstrings(encryptedMessageSubtrings);

        return encryptedMessage;
    }

    // TODO : Move to specialized class
    private static List<Long> applyKey(List<Long> textsBytes, long keyBytes, Operation<Long> operation) {
        return textsBytes
                .stream()
                .map(textBytes -> applyKey(textBytes, keyBytes, operation))
                .collect(Collectors.toList());
    }

    // TODO : Move to specialized class
    private static long applyKey(long textBytes, long keyBytes, Operation<Long> operation) {
        return operation.operate(textBytes, keyBytes);
    }

    // TODO : Move to specialized class
    private static List<String> getSubstrings(String text, int substringLength) {
        List<String> substrings = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            substrings.add(getSubstring(text, i, substringLength));
        }

        return substrings;
    }

    // TODO : Move to specialized class
    private static String getSubstring(String text, int index, int substringLength) {
        return text.substring(index, Math.min(index + substringLength, text.length()));
    }

    // TODO : Move to specialized class
    private static List<Long> getBytes(List<String> texts) {
        return texts
                .stream()
                .map(OperationService::getByte)
                .collect(Collectors.toList());
    }

    // TODO : Move to specialized class
    private static long getByte(String text) {
        // TODO : Move 2 to static final
        return Long.parseLong(text, 2);
    }

    // TODO : Move to specialized class
    private static List<String> getTexts(List<Long> bytes) {
        return bytes
                .stream()
                .map(OperationService::getText)
                .collect(Collectors.toList());
    }

    // TODO : Move to specialized class
    private static String getText(long bytes) {
        return Long.toBinaryString(bytes);
    }

    // TODO : Move to specialized class
    private static String concatSubstrings(List<String> texts) {
        return String.join(" ", texts);
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
