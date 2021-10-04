package ca.ulaval.glo3100.friedman;

import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.utils.IndexOfCoincidenceUtils;
import ca.ulaval.glo3100.utils.ShiftedTextUtils;

import java.util.*;

public class FriedmanKeyLengthFinder {

    // We assume that the plain text is in english
    private static final double ENGLISH_INDEX_OF_COINCIDENCE = 0.066;
    private static final int MIN_KEY_LENGTH = 1;
    private static final int MAX_KEY_LENGTH = 9;

    /**
     * @param cypherText cypher text to get key length from
     * @return key length of given cypher text
     */
    public static int findKeyLength(String cypherText) {
        Logger.logDebug("Finding key length using Friedman test");

        Logger.logDebug(String.format("Given cypher text : %s", cypherText));

        // Initial values
        double closestDistance = Double.MAX_VALUE;
        int closestKeyLength = MIN_KEY_LENGTH;

        for (int keyLength = MIN_KEY_LENGTH; keyLength <= MAX_KEY_LENGTH; keyLength++) {
            List<String> subtexts = ShiftedTextUtils.getSubtexts(cypherText, keyLength);
            List<Double> indexesOfCoincidence = IndexOfCoincidenceUtils.calculateIndexesOfCoincidence(subtexts);
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

        Logger.logInfo(String.format("Key length : %d", closestKeyLength));

        return closestKeyLength;
    }
}
