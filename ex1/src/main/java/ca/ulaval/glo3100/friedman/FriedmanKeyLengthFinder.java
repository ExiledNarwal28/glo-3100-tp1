package ca.ulaval.glo3100.friedman;

import ca.ulaval.glo3100.console.Logger;

import java.util.HashMap;
import java.util.Map;

public class FriedmanKeyLengthFinder {

    // We assume that the plain text is in english
    private static final double ENGLISH_INDEX_OF_COINCIDENCE = 0.066;
    private static final int MIN_KEY_LENGTH = 1;
    private static final int MAX_KEY_LENGTH = 9;

    public void findKeyLength(String cypherText) {
        Logger.logDebug("Finding key length using Friedman test");

        Logger.logDebug(String.format("Given cypher text : %s", cypherText));

        Map<Character, Integer> occurrences = calculateOccurrences(cypherText);

        if (Logger.isDebugging) {
            occurrences.forEach((character, occurrence) -> Logger.logDebug(String.format("%c : %d", character, occurrence)));
        }

        // TODO : Calculate index of coincidence for a key length of 1
        // TODO : Calculate indexes of coincidence for a given key length
        // TODO : Map tested key length to average index of coincidence
        // TODO : Find average index of coincidence closest to english
        // TODO : Return key length
    }

    private Map<Character, Integer> calculateOccurrences(String cypherText) {
        Map<Character, Integer> occurrences = new HashMap<>();

        for (Character character : cypherText.toCharArray()) {
            if (occurrences.containsKey(character)) {
                occurrences.put(character, occurrences.get(character) + 1);
            } else {
                occurrences.put(character, 1);
            }
        }

        return occurrences;
    }
}
