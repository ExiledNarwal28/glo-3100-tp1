package ca.ulaval.glo3100.decryption;

import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.utils.CharacterOccurrenceUtils;
import ca.ulaval.glo3100.utils.ShiftedTextUtils;
import ca.ulaval.glo3100.utils.VectorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VigenereDecrypter {

    private static final int MIN_SHIFT = 0;
    private static final int MAX_SHIFT = 25;

    // TODO : Add javadoc
    public static String decrypt(String cypherText, int keyLength) {
        Logger.logDebug("Decrypting cypher text");

        List<String> subtexts = ShiftedTextUtils.getSubtexts(cypherText, keyLength);
        List<String> shiftedSubtexts = new ArrayList<>();

        for (String subtext : subtexts) {
            Logger.logDebug(String.format("Calculating mutual index of coincidence for subtext : %s", subtext));

            // Initial values
            String shiftedSubtext = subtext;
            double highestScalarProduct = 0;

            for(int shift = MIN_SHIFT; shift <= MAX_SHIFT; shift++) {
                String possibleShiftedSubtext = ShiftedTextUtils.shiftText(subtext, shift);
                // TODO : Would a List<Double> be enough?
                List<Double> distribution = CharacterOccurrenceUtils.getLettersDistribution(possibleShiftedSubtext);
                List<Double> englishDistribution = CharacterOccurrenceUtils.getEnglishLettersDistribution();

                Double scalarProduct = VectorUtils.getScalarProduct(distribution, englishDistribution);

                if (scalarProduct > highestScalarProduct) {
                    shiftedSubtext = possibleShiftedSubtext;
                    highestScalarProduct = scalarProduct;
                }
            }

            shiftedSubtexts.add(shiftedSubtext);
        }

        return ShiftedTextUtils.getText(shiftedSubtexts);
    }
}
