package ca.ulaval.glo3100.friedman;

import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.utils.IndexOfCoincidenceUtils;
import ca.ulaval.glo3100.utils.ShiftedTextUtils;

import java.util.ArrayList;
import java.util.List;

public class FriedmanKeyLengthFinder {

    // We assume that the plain text is in english
    private static final double ENGLISH_INDEX_OF_COINCIDENCE = 0.066;
    private static final int MIN_KEY_LENGTH = 1;
    private static final int MAX_KEY_LENGTH = 9;

    public static int findKeyLength(String cypherText) {
        Logger.logDebug("Finding key length using Friedman test");

        Logger.logDebug(String.format("Given cypher text : %s", cypherText));

        double closestDistance = Double.MAX_VALUE; // initial closest distance
        int closestKeyLength = MIN_KEY_LENGTH; // initial closest key length

        for (int keyLength = MIN_KEY_LENGTH; keyLength <= MAX_KEY_LENGTH; keyLength++) {
            List<Double> indexesOfCoincidence = calculateIndexesOfCoincidence(cypherText, keyLength);
            StringBuilder indexesBuilder = new StringBuilder();
            double average = 0;

            for (Double indexOfCoincidence : indexesOfCoincidence) {
                indexesBuilder.append(String.format("%f ", indexOfCoincidence));
                average += indexOfCoincidence;
            }

            average = average / indexesOfCoincidence.size();

            Logger.logDebug(String.format("Indexes of coincidence for key length of %d -> %s (average : %f)", keyLength, indexesBuilder, average));

            double averageDistance = Math.abs(average - ENGLISH_INDEX_OF_COINCIDENCE);
            if (averageDistance < closestDistance) {
                closestDistance = averageDistance;
                closestKeyLength = keyLength;
            }
        }

        Logger.logDebug(String.format("Closest key length found : %d", closestKeyLength));

        return closestKeyLength;
    }

    private static List<Double> calculateIndexesOfCoincidence(String text, int keyLength) {
        List<String> subtexts = ShiftedTextUtils.getSubtexts(text, keyLength);
        List<Double> indexesOfCoincidence = new ArrayList<>();

        for (String subtext : subtexts) {
            double indexOfCoincidence = IndexOfCoincidenceUtils.calculateIndexOfCoincidence(subtext);
            indexesOfCoincidence.add(indexOfCoincidence);
        }

        return indexesOfCoincidence;
    }
}
