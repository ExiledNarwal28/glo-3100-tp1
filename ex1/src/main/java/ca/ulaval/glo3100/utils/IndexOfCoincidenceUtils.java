package ca.ulaval.glo3100.utils;

import java.util.*;

public class IndexOfCoincidenceUtils {

    // TODO : Add javadoc
    public static List<Double> calculateIndexesOfCoincidence(String text, int keyLength) {
        List<String> subtexts = ShiftedTextUtils.getSubtexts(text, keyLength);
        List<Double> indexesOfCoincidence = new ArrayList<>();

        for (String subtext : subtexts) {
            indexesOfCoincidence.add(calculateIndexOfCoincidence(subtext));
        }

        return indexesOfCoincidence;
    }

    // TODO : Add javadoc
    private static double calculateIndexOfCoincidence(String text) {
        Collection<Integer> occurences = CharacterOccurrenceUtils.calculateOccurrences(text);
        double indexOfCoincidence = 0;

        // Sum of : n_1 * (n_1 - 1)
        for (Integer occurrence : occurences) {
            indexOfCoincidence += occurrence * (occurrence - 1);
        }

        // Divide by : n / (n - 1)
        indexOfCoincidence = indexOfCoincidence / (text.length() * (text.length() - 1));

        return indexOfCoincidence;
    }
}
