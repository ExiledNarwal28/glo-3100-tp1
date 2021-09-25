package ca.ulaval.glo3100.decryption;

import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.utils.CharacterOccurrenceUtils;
import ca.ulaval.glo3100.utils.IndexOfCoincidenceUtils;
import ca.ulaval.glo3100.utils.ShiftedTextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VigenereDecrypter {

    private static final int MAX_SHIFT = 25;

    // TODO : Add javadoc
    public static String decrypt(String cypherText, int keyLength) {
        Logger.logDebug("Decrypting cypher text");

        List<String> subtexts = ShiftedTextUtils.getSubtexts(cypherText, keyLength);
        List<String> shiftedSubtexts = new ArrayList<>();

        for (String subtext : subtexts) {
            Logger.logDebug(String.format("Calculating mutual index of coincidence for subtext : %s", subtext));

            List<String> possibleShiftedSubtexts = new ArrayList<>();

            for (int shift = 0; shift <= MAX_SHIFT; shift++) {
                possibleShiftedSubtexts.add((ShiftedTextUtils.shiftText(subtext, shift)));
            }

            // TODO : This uses indexes of coincidence, but not mutually
            Map<String, Double> indexesOfCoincidence = IndexOfCoincidenceUtils.buildMapOfIndexesOfCoincidence(possibleShiftedSubtexts);

            // Initial values
            String shiftedSubtext = subtext;
            double highestIndexOfCoincidence = 0;

            for (Map.Entry<String, Double> entry : indexesOfCoincidence.entrySet()) {
                if (entry.getValue() > highestIndexOfCoincidence) {
                    shiftedSubtext = entry.getKey();
                    highestIndexOfCoincidence = entry.getValue();
                }
            }

            shiftedSubtexts.add(shiftedSubtext);
        }

        return ShiftedTextUtils.getText(shiftedSubtexts);
    }
}
