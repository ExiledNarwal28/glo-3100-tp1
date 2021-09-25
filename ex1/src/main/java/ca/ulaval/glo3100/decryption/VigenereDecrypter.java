package ca.ulaval.glo3100.decryption;

import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.utils.CharacterOccurrenceUtils;
import ca.ulaval.glo3100.utils.ShiftedTextUtils;

import java.util.ArrayList;
import java.util.List;

public class VigenereDecrypter {

    private static final char ENGLISH_MOST_FREQUENT_LETTER = 'E';

    // TODO : Add javadoc
    public static String decrypt(String cypherText, int keyLength) {
        Logger.logDebug("Decrypting cypher text");

        List<String> subtexts = ShiftedTextUtils.getSubtexts(cypherText, keyLength);

        // TODO : Review everything under here.
        List<String> unShiftedSubtexts = new ArrayList<>();

        for (String subtext : subtexts) {
            Logger.logDebug(String.format("Calculating most frequent character for subtext : %s", subtext));

            char mostFrequentCharacter = CharacterOccurrenceUtils.findMostFrequentCharacter(subtext);

            int shift = mostFrequentCharacter - ENGLISH_MOST_FREQUENT_LETTER;

            Logger.logDebug(String.format("--> Most frequent character : %s (shift from '%s' : %d)", mostFrequentCharacter, ENGLISH_MOST_FREQUENT_LETTER, shift));

            String unShiftedSubtext = ShiftedTextUtils.unShiftText(subtext, shift);
            unShiftedSubtexts.add(unShiftedSubtext);

            Logger.logDebug(String.format("Un-shifted subtext : %s", unShiftedSubtext));
        }

        // TODO : Review everything over here.

        // TODO : Calculate shift (via mutual index of coincidence) of Y1 to Y0, ..., Y1 to YK
        //        Make sure if key length = 1 it still works
        //        See next TODOs
        // TODO : Calculate scalar products of each possible shifts of Y1 to Y0
        // TODO : Find maximal scalar product, that is the shift
        // TODO : Do that for each subtexts, n > 1

        return ShiftedTextUtils.getText(unShiftedSubtexts);
    }
}
