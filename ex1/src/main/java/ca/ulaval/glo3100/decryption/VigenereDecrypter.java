package ca.ulaval.glo3100.decryption;

import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.utils.ShiftedTextUtils;

import java.util.List;

public class VigenereDecrypter {

    public static String decrypt(String cypherText, int keyLength) {
        Logger.logDebug("Decrypting cypher text");

        List<String> subtexts = ShiftedTextUtils.getSubtexts(cypherText, keyLength);
        // TODO : Find most frequent letter in each subtext (that's "E")
        // TODO : Get shift size for most frequent letter
        // TODO : Un-shift each subtext
        // TODO : Assemble back plain text

        return "";
    }
}
