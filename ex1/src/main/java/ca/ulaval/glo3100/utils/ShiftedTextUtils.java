package ca.ulaval.glo3100.utils;

import java.util.ArrayList;
import java.util.List;

public class ShiftedTextUtils {

    private static final int FIRST_POSSIBLE_LETTER_POSITION = 'A';
    private static final int LAST_POSSIBLE_LETTER_POSITION = 'Z';

    /**
     * @param text text to be shifted into subtexts
     * @param keyLength length of cypher key
     * @return list of shifted subtexts
     */
    public static List<String> getSubtexts(String text, int keyLength) {
        List<String> subtexts = new ArrayList<>();

        for (int i = 1; i <= keyLength; i++) {
            StringBuilder subtextBuilder = new StringBuilder();

            for (int j = i - 1; j < text.length(); j += keyLength) {
                subtextBuilder.append(text.charAt(j));
            }

            subtexts.add(subtextBuilder.toString());
        }

        return subtexts;
    }

    /**
     * @param subtexts list of each shifted subtexts
     * @return original text
     */
    public static String getText(List<String> subtexts) {
        String joinedSubtexts = String.join("", subtexts);
        StringBuilder text = new StringBuilder();
        int i = 0;

        for (int j = 0; j < joinedSubtexts.length(); j += subtexts.size()) {
            for (String subtext : subtexts) {
                if (subtext.length() > i) {
                    text.append(subtext.charAt(i));
                }
            }

            i++;
        }

        return text.toString();
    }

    /**
     * @param shifts list of shifts from first possible letter
     * @return assembled string from given shifts
     */
    public static String getTextFromShifts(List<Integer> shifts) {
        StringBuilder text = new StringBuilder();

        for (Integer shift : shifts) {
            text.append((char) (FIRST_POSSIBLE_LETTER_POSITION + shift));
        }

        return text.toString();
    }

    /**
     * @param text original text
     * @param shift shift from first possible letter to apply to each letter of the text
     * @return shifted text
     */
    public static String shiftText(String text, int shift) {
        StringBuilder shiftedText = new StringBuilder();

        for (char character : text.toCharArray()) {
            shiftedText.append(shiftCharacter(character, shift));
        }

        return shiftedText.toString();
    }

    /**
     * @param character original character
     * @param shift shift from first possible letter to apply to character
     * @return shifted character
     */
    private static char shiftCharacter(char character, int shift) {
        // TODO : See if this line is necessary
        int characterPosition = character;
        int shiftedCharacterPosition = characterPosition - shift;

        if (shiftedCharacterPosition < FIRST_POSSIBLE_LETTER_POSITION) {
            shiftedCharacterPosition = (LAST_POSSIBLE_LETTER_POSITION + 1) - (shift - (characterPosition - FIRST_POSSIBLE_LETTER_POSITION));
        } else if (shiftedCharacterPosition > LAST_POSSIBLE_LETTER_POSITION) {
            shiftedCharacterPosition = FIRST_POSSIBLE_LETTER_POSITION + (shift - (LAST_POSSIBLE_LETTER_POSITION - characterPosition));
        }

        return (char)(shiftedCharacterPosition);
    }
}
