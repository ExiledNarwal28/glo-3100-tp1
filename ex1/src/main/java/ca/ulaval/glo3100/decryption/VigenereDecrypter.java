package ca.ulaval.glo3100.decryption;

import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.utils.CharacterOccurrenceUtils;
import ca.ulaval.glo3100.utils.ShiftedTextUtils;

import java.util.ArrayList;
import java.util.List;

public class VigenereDecrypter {

    private static final char ENGLISH_MOST_FREQUENT_LETTER = 'E';
    private static final int FIRST_POSSIBLE_LETTER_POSITION = 'A';
    private static final int LAST_POSSIBLE_LETTER_POSITION = 'Z';

    public static String decrypt(String cypherText, int keyLength) {
        Logger.logDebug("Decrypting cypher text");

        List<String> subtexts = ShiftedTextUtils.getSubtexts(cypherText, keyLength);
        List<String> unshiftedSubtexts = new ArrayList<>();

        for (String subtext : subtexts) {
            Logger.logDebug(String.format("Calculating most frequent character for subtext : %s", subtext));

            char mostFrequentCharacter = CharacterOccurrenceUtils.findMostFrequentCharacter(subtext);

            int shift = mostFrequentCharacter - ENGLISH_MOST_FREQUENT_LETTER;

            Logger.logDebug(String.format("--> Most frequent character : %s (shift from '%s' : %d)", mostFrequentCharacter, ENGLISH_MOST_FREQUENT_LETTER, shift));

            String unShiftedSubtext = unShiftText(subtext, shift);
            unshiftedSubtexts.add(unShiftedSubtext);

            Logger.logDebug(String.format("Un-shifted subtext : %s", unShiftedSubtext));
        }

        return ShiftedTextUtils.getText(unshiftedSubtexts);
    }

    private static String unShiftText(String text, int shift) {
        StringBuilder unShiftedText = new StringBuilder();
        for (char character : text.toCharArray()) {
            int characterPosition = character;
            int unShiftedCharacterPosition = characterPosition - shift;

            if (unShiftedCharacterPosition < FIRST_POSSIBLE_LETTER_POSITION) {
                unShiftedCharacterPosition = (LAST_POSSIBLE_LETTER_POSITION + 1) - (shift - (characterPosition - FIRST_POSSIBLE_LETTER_POSITION));
            } else if (unShiftedCharacterPosition > LAST_POSSIBLE_LETTER_POSITION) {
                unShiftedCharacterPosition = FIRST_POSSIBLE_LETTER_POSITION + (shift - (LAST_POSSIBLE_LETTER_POSITION - characterPosition));
            }

            char unShifterCharacter = (char)(unShiftedCharacterPosition);
            unShiftedText.append(unShifterCharacter);
        }

        return unShiftedText.toString();
    }
}
