package ca.ulaval.glo3100.decryption;

import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.utils.CharacterOccurrenceUtils;
import ca.ulaval.glo3100.utils.ShiftedTextUtils;

import java.util.List;

public class VigenereDecrypter {

    private static final char ENGLISH_MOST_FREQUENT_LETTER = 'E';

    public static String decrypt(String cypherText, int keyLength) {
        Logger.logDebug("Decrypting cypher text");

        List<String> subtexts = ShiftedTextUtils.getSubtexts(cypherText, keyLength);

        for (String subtext : subtexts) {
            Logger.logDebug(String.format("Calculating most frequent character for subtext : %s", subtext));

            char mostFrequentCharacter = CharacterOccurrenceUtils.findMostFrequentCharacter(subtext);

            int shift = mostFrequentCharacter - ENGLISH_MOST_FREQUENT_LETTER;

            Logger.logDebug(String.format("--> Most frequent character : %s (shift from '%s' : %d)", mostFrequentCharacter, ENGLISH_MOST_FREQUENT_LETTER, shift));

            // TODO : Un-shift each subtext
            // TODO : Assemble back plain text
        }

        return "";
    }
}
