package ca.ulaval.glo3100.friedman;

import ca.ulaval.glo3100.console.Logger;

import java.util.*;

public class FriedmanKeyLengthFinder {

    // We assume that the plain text is in english
    private static final double ENGLISH_INDEX_OF_COINCIDENCE = 0.066;
    private static final int MIN_KEY_LENGTH = 1;
    private static final int MAX_KEY_LENGTH = 9;

    public void findKeyLength(String cypherText) {
        Logger.logDebug("Finding key length using Friedman test");

        Logger.logDebug(String.format("Given cypher text : %s", cypherText));

        for (int keyLength = MIN_KEY_LENGTH; keyLength <= MAX_KEY_LENGTH; keyLength++) {
            List<Double> indexesOfCoincidence = calculateIndexesOfCoincidence(cypherText, keyLength);
            StringBuilder indexesBuilder = new StringBuilder();

            for (Double indexOfCoincidence : indexesOfCoincidence) {
                indexesBuilder.append(String.format("%f ", indexOfCoincidence));
            }

            Logger.logDebug(String.format("Indexes of coincidence for key length of %d -> %s", keyLength, indexesBuilder));
        }

        // TODO : Map tested key length to average index of coincidence
        // TODO : Find average index of coincidence closest to english
        // TODO : Return key length
    }

    private List<Double> calculateIndexesOfCoincidence(String text, int keyLength) {
        List<Double> indexesOfCoincidence = new ArrayList<>();

        for (int i = 1; i <= keyLength; i++) {
            StringBuilder subtextBuilder = new StringBuilder();

            for (int j = i - 1; j < text.length(); j += keyLength) {
                subtextBuilder.append(text.charAt(j));
            }

            indexesOfCoincidence.add(calculateIndexOfCoincidence(subtextBuilder.toString()));
        }

        return indexesOfCoincidence;
    }

    private double calculateIndexOfCoincidence(String text) {
        Collection<Integer> occurences = calculateOccurrences(text);
        double indexOfCoincidence = 0;

        // Sum of : n_1 * (n_1 - 1)
        for (Integer occurrence : occurences) {
            indexOfCoincidence += occurrence * (occurrence - 1);
        }

        // Divide by : n / (n - 1)
        indexOfCoincidence = indexOfCoincidence / (text.length() * (text.length() - 1));

        return indexOfCoincidence;
    }

    private Collection<Integer> calculateOccurrences(String text) {
        Map<Character, Integer> occurrences = new HashMap<>();

        for (Character character : text.toCharArray()) {
            if (occurrences.containsKey(character)) {
                occurrences.put(character, occurrences.get(character) + 1);
            } else {
                occurrences.put(character, 1);
            }
        }

        return occurrences.values();
    }
}
