package ca.ulaval.glo3100.utils;

import java.util.*;

public class CharacterOccurrenceUtils {

    public static Collection<Integer> calculateOccurrences(String text) {
        return getMapOfOccurrences(text).values();
    }

    public static char findMostFrequentCharacter(String text) {
        Map<Character, Integer> occurrences = getMapOfOccurrences(text);

        // Initial values
        char mostFrequentCharacter = 'A';
        int mostOccurrences = 0;

        for (Map.Entry<Character, Integer> entry : occurrences.entrySet()) {
            Integer occurrence = entry.getValue();

            if (occurrence > mostOccurrences) {
                mostOccurrences = occurrence;
                mostFrequentCharacter = entry.getKey();
            }
        }

        return mostFrequentCharacter;
    }

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
