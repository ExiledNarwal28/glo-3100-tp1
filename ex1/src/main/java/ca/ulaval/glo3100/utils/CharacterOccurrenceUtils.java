package ca.ulaval.glo3100.utils;

import java.util.*;

public class CharacterOccurrenceUtils {

    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * See https://en.wikipedia.org/wiki/Letter_frequency
     */
    private static final List<Double> ENGLISH_LETTERS_DISTRIBUTION = new ArrayList<>(Arrays.asList(
            0.082,
            0.015,
            0.028,
            0.043,
            0.013,
            0.022,
            0.02,
            0.061,
            0.07,
            0.0015,
            0.0077,
            0.04,
            0.024,
            0.067,
            0.075,
            0.019,
            0.00095,
            0.06,
            0.063,
            0.091,
            0.028,
            0.0098,
            0.024,
            0.0015,
            0.02,
            0.00074
    ));

    /**
     * @param text text to calculate occurrences from
     * @return list of each number of occurrences for each letter in given text
     */
    public static Collection<Integer> calculateOccurrences(String text) {
        return getMapOfOccurrences(text).values();
    }

    /**
     * @param text text to calculate distribution from
     * @return map of each character and their respective distribution in given text
     */
    public static List<Double> getLettersDistribution(String text) {
        List<Double> distribution = new ArrayList<>();
        Map<Character, Integer> occurences = getMapOfOccurrences(text);

        for (char letter : ALPHABET) {
            if (occurences.containsKey(letter)) {
                distribution.add((double) occurences.get(letter) / text.length());
            }
            else
            {
                distribution.add(0d);
            }
        }

        return distribution;
    }

    /**
     * @return distribution of letters in the english language
     */
    public static List<Double> getEnglishLettersDistribution() {
        return ENGLISH_LETTERS_DISTRIBUTION;
    }

    /**
     * @param text text to calculate map of occurrences from
     * @return map of each character and their respective number of occurrences in given text
     */
    private static Map<Character, Integer> getMapOfOccurrences(String text) {
        Map<Character, Integer> occurrences = new HashMap<>();

        for (Character character : text.toCharArray()) {
            if (occurrences.containsKey(character)) {
                occurrences.put(character, occurrences.get(character) + 1);
            } else {
                occurrences.put(character, 1);
            }
        }

        return occurrences;
    }
}
