package ca.ulaval.glo3100.utils;

import java.util.Collection;

public class IndexOfCoincidenceUtils {

    private static final double ENGLISH_INDEX_OF_COINCIDENCE = 0.066;

    // TODO : Move things from friedman here

    public static int findMostPlausibleShift(String text) {
        // Initial values
        double closestDistance = Double.MAX_VALUE;
        int mostPlausibleShift = 0;

        for (int shift = 0; shift < 26; shift++) {
            String unShiftedText = ShiftedTextUtils.unShiftText(text, shift);
            // TODO : Nope, that's not it.
            double indexOfCoincidence = calculateIndexOfCoincidence(unShiftedText);

            double distance = Math.abs(indexOfCoincidence - ENGLISH_INDEX_OF_COINCIDENCE);
            if (distance < closestDistance) {
                closestDistance = distance;
                mostPlausibleShift = shift;
            }
        }

        return mostPlausibleShift;
    }

    public static double calculateIndexOfCoincidence(String text) {
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
