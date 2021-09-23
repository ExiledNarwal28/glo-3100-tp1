package ca.ulaval.glo3100.friedman;

import ca.ulaval.glo3100.console.Logger;

import java.util.HashMap;
import java.util.Map;

public class FriedmanKeyLengthFinder {

    public void findKeyLength(String cypherText) {
        Logger.logDebug("Finding key length using Friedman test");

        Logger.logDebug(String.format("Given cypher text : %s", cypherText));

        Map<Character, Integer> occurrences = calculateOccurrences(cypherText);

        if (Logger.isDebugging) {
            occurrences.forEach((character, occurrence) -> Logger.logDebug(String.format("%c : %d", character, occurrence)));
        }

        // TODO : Find key length
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
