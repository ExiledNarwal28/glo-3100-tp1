package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.console.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static ca.ulaval.glo3100.utils.StringUtils.concatStrings;

public class ByteUtils {

    public static List<Long> getBytes(List<String> texts) {
        Logger.logDebug(String.format("Texts to get bytes : %s", concatStrings(texts)));

        List<Long> bytes = texts
                .stream()
                .map(ByteUtils::getByte)
                .collect(Collectors.toList());

        Logger.logDebug(String.format("  -> %s", concatStrings(getTexts(bytes))));

        return bytes;
    }

    public static long getByte(String text) {
        // TODO : Move 2 to static final
        return Long.parseLong(text, 2);
    }

    public static List<String> getTexts(List<Long> bytes) {
        // TODO : Refactor 8
        return getTexts(bytes, 8);
    }

    public static List<String> getTexts(List<Long> bytes, int byteLength) {
        return bytes
                .stream()
                .map((singleByte) -> getText(singleByte, byteLength))
                .collect(Collectors.toList());
    }

    public static String getText(long bytes) {
        // TODO : Refactor 8
        return getText(bytes, 8);
    }

    public static String getText(long bytes, int byteLength) {
        String text = Long.toBinaryString(bytes);
        int neededPadding = byteLength - text.length();

        if (neededPadding > 0) {
            String padding = new String(new char[neededPadding]).replace('\0', '0');

            return padding + text;
        } else {
            return text;
        }
    }
}
