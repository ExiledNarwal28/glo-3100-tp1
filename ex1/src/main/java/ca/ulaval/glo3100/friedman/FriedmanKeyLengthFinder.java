package ca.ulaval.glo3100.friedman;

import ca.ulaval.glo3100.console.Logger;

import java.util.HashMap;
import java.util.Map;

public class FriedmanKeyLengthFinder {

    public void findKeyLength(String cypherText) {
        Logger.log("Finding key length using Friedman test");

        Logger.log(String.format("Given cypher text : %s", cypherText));

        Map<Character, Integer> occurrences = calculateOccurrences(cypherText);

        occurrences.forEach((character, occurrence) -> Logger.log(String.format("%c : %d", character, occurrence)));

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
