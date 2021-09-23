package ca.ulaval.glo3100.friedman;

import ca.ulaval.glo3100.console.Logger;

import java.util.Collection;
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

        double indexOfCoincidence = calculateIndexOfCoincidence(occurrences.values(), cypherText.length());

        Logger.logDebug(String.format("Index of coincidence for key size of 1 : %f", indexOfCoincidence));

        // TODO : Calculate indexes of coincidence for a given key length
        // TODO : Map tested key length to average index of coincidence
        // TODO : Find average index of coincidence closest to english
        // TODO : Return key length
    }

    private Map<Character, Integer> calculateOccurrences(String text) {
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

    private double calculateIndexOfCoincidence(Collection<Integer> occurences, double textSize) {
        double indexOfCoincidence = 0;

        // Sum of : n_1 * (n_1 - 1)
        for (Integer occurrence : occurences) {
            indexOfCoincidence += occurrence * (occurrence - 1);
        }

        // Divide by : n / (n - 1)
        indexOfCoincidence = indexOfCoincidence / (textSize * (textSize - 1));

        return indexOfCoincidence;
    }
}
