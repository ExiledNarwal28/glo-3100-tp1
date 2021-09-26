package ca.ulaval.glo3100.utils;

import java.util.*;

public class IndexOfCoincidenceUtils {

    /**
     * @param subtexts list of subtexts to calculate each index of coincidences
     * @return list of indexes of coincidence for given subtexts
     */
    public static List<Double> calculateIndexesOfCoincidence(List<String> subtexts) {
        List<Double> indexesOfCoincidence = new ArrayList<>();

        for (String subtext : subtexts) {
            indexesOfCoincidence.add(calculateIndexOfCoincidence(subtext));
        }

        return indexesOfCoincidence;
    }

    /**
     * @param text text to calculate index of coincidences
     * @return Index of coincidence for given text
     */
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
