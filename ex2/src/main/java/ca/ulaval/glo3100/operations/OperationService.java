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
    // TODO : Remove if not needed
    private static final Operation<Long> AND = ((firstBytes, secondBytes) -> firstBytes & secondBytes);

    private static String ecb(String message, String key) {
        // TODO : Opposite for decryption
        // TODO : Move 8 to static final
        List<String> substrings = getSubstrings(message, 8);
        List<Long> substringsBytes = getBytes(substrings);
        long keyBytes = getByte(key);
        List<Long> encryptedBytes = applyKey(substringsBytes, keyBytes, XOR);
        List<String> encryptedMessageSubstrings = getTexts(encryptedBytes);

        return concatTexts(encryptedMessageSubstrings);
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
        Logger.logDebug(String.format("Applying key (%s) to text : %s", getText(keyBytes), getText(textBytes)));

        long encryptedText = operation.operate(textBytes, keyBytes);

        Logger.logDebug(String.format("  -> : %s", getText(encryptedText)));

        return encryptedText;
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
    // TODO : Remove this method, probably useless
    // TODO : This will need to be generalized, not all operations need substrings of 8 length
    /*
    private static List<String> applyPadding(List<String> texts) {
        if (texts.size() == 1) {
            return texts;
        }

        int substringsLength = 8;

        String lastString = texts.get(texts.size() - 1);
        int neededPadding = substringsLength % lastString.length();

        Logger.logDebug(String.format("Needed padding : %d",neededPadding));

        if (neededPadding > 0) {
            String padding = new String(new char[neededPadding]).replace('\0', '0');
            List<String> paddedTexts = new ArrayList<>();

            for (String text : texts) {
                String newPadding = text.substring(text.length() - neededPadding - 1);

                paddedTexts.add(padding + text.substring(0, text.length() - neededPadding + 1));

                padding = newPadding;
            }

            return paddedTexts;
        } else {
            return texts;
        }
    }
    */

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
