package ca.ulaval.glo3100.utils;

import java.util.ArrayList;
import java.util.List;

public class ShiftedTextUtils {

    private static final int FIRST_POSSIBLE_LETTER_POSITION = 'A';
    private static final int LAST_POSSIBLE_LETTER_POSITION = 'Z';

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

    public static String unShiftText(String text, int shift) {
        StringBuilder unShiftedText = new StringBuilder();
        for (char character : text.toCharArray()) {
            int characterPosition = character;
            int unShiftedCharacterPosition = characterPosition - shift;

            // TODO : This logic should be moved
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
