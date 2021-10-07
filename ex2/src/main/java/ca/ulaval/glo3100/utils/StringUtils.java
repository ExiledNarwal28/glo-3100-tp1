package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.console.Logger;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static List<String> getSubstrings(String string, int substringLength) {
        Logger.logDebug(String.format("Text to get substrings : %s", string));

        List<String> substring = new ArrayList<>();

        for (int i = 0; i < string.length(); i += substringLength) {
            substring.add(getSubstring(string, i, substringLength));
        }

        Logger.logDebug(String.format("  -> %s", concatStrings(substring)));

        return substring;
    }

    public static List<String> getSubstrings(String string, int substringLength, int firstSubstringLength) {
        List<String> substring = new ArrayList<>();

        substring.add(getSubstring(string, 0, firstSubstringLength));

        String cutString = getSubstring(string, firstSubstringLength, string.length() - firstSubstringLength);
        substring.addAll(getSubstrings(cutString, substringLength));

        return substring;
    }

    public static String getSubstring(String text, int index, int substringLength) {
        return text.substring(index, Math.min(index + substringLength, text.length()));
    }

    public static String concatStrings(List<String> strings) {
        return String.join(" ", strings);
    }
}
