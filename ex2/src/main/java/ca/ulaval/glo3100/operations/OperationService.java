package ca.ulaval.glo3100.operations;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.console.Logger;

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
    private static final Operation<Long> XOR = ((firstBytes, secondBytes) -> firstBytes ^ secondBytes);
    private static final Operation<Long> OR = ((firstBytes, secondBytes) -> firstBytes & secondBytes);

    // TODO : Something doesn't work with bytes conversion / XOR (should be shifted by two bytes)
    private static String ecb(String message, String key) {
        // TODO : Opposite for decryption
        // TODO : Move 8 to static final
        List<String> substrings = getSubstrings(message, 8);
        List<Long> substringsBytes = getBytes(substrings);
        long keyBytes = getByte(key);
        List<Long> encryptedBytes = applyKey(substringsBytes, keyBytes, XOR);
        List<String> encryptedMessageSubtrings = getTexts(encryptedBytes);

        return concatTexts(encryptedMessageSubtrings);
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
        Logger.logDebug(String.format("Text to get substrings : %s", text));

        List<String> substrings = new ArrayList<>();

        for (int i = 0; i < text.length(); i += substringLength) {
            substrings.add(getSubstring(text, i, substringLength));
        }

        Logger.logDebug(String.format("  -> %s", concatTexts(substrings)));

        return substrings;
    }

    // TODO : Move to specialized class
    private static String getSubstring(String text, int index, int substringLength) {
        return text.substring(index, Math.min(index + substringLength, text.length()));
    }

    // TODO : Move to specialized class
    private static List<Long> getBytes(List<String> texts) {
        Logger.logDebug(String.format("Texts to get bytes : %s", concatTexts(texts)));

        List<Long> bytes = texts
                .stream()
                .map(OperationService::getByte)
                .collect(Collectors.toList());

        Logger.logDebug(String.format("  -> %s", concatTexts(getTexts(bytes))));

        return bytes;
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
    private static String concatTexts(List<String> texts) {
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
