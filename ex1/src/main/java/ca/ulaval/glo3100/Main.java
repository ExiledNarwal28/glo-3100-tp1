package ca.ulaval.glo3100;

import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.friedman.FriedmanKeyLengthFinder;

public class Main {

    private static final String CYPHER_TEXT = "YHKMVMSXVVXTMGVWMRCJKSQREJYCBEJYIVODEFDSCTIRTPESMPVPITSYEGBYTNZSXWLCTLSSXBVTBOGYJWYFGIEZORXCEKYRHCEEBYMHWSBMENZSXCEFCINPPUZRDEBNZRDOYHZRDEPFZKSBPYKIHTGUCPODGCGLORXYOXNEGLPTDISHZWDHILVZORWYZRYTLYIAYRHMDSFIRAWVYMXBVYXIRNVPVIKCSPOCMJYIBTIRKFKCONFTVAMHKIHTEWZTREVCJEZAMLFJKLKIIMDHQMKLKTGLVEDEXBVIXCVSGXSORUEHDHILVZORWCEKNEGLPTDISHKLODINRMVEHIGIBAXCFRYFEWZTREVCJGYNXLFPVEHVFXRBCNYIKLKIIMDHQUEHSNIUTLSNWNRRMEFSROOYXBZWSSEMVGBEXCUIKLPSBRYWRIEPITSNYIMOQGLRSCEHKWESYUCPIAWBFVDSXLZRQOJWYEBAGNVVCWLCTLSSRYVHODXIUIMRCJKXREGCGLORXYOXKCVSGXYSCMKIWIWNYIYRHYIINLMMKSPEPYDIXTWIWJSNMNVTYSWCSPOPPUZRDEBNJJSNMNVTYSWCSPOCCJYIBTIRKWPIRCKIZOWMZFVEOYPWKNHNYIONGLPTDISHRRNDIWICZTMIEEVGSLZXRMWQYMMHGIIVOSTIEHDOIUTLUECEVCCAVYZQZOVNRRDAWWZTREVMNMDHSOKZKRMUSPOKISJGKNFYKVSVMUCPIBVIBIXWMNYSXLCNYIUNSQCINGIIWXREGCGLORYMVHKNHUIIDHILVJYRIOJIVEWMFVOVIHTSENXYITBOHOTXSVIZFVWOWNGYBPSMVWRIWNFVSCEFCCMITBVVCWILVSPTIHLWODHCIIMTPSWSBERWICZTMIESBDIWICZTMIEASTLILXKDHCKMYNEFGVYCIXLVOSWOTLKSEOKLONXCTEDISHFVSNXYXVSTCWYIMKWGVWCAKYTPKIVY";

    private static final String ARG_FRIEDMAN = "friedman";
    private static final String ARG_DECRYPT = "decrypt";
    private static final String ARG_DEBUG = "debug";

    public static void main(String[] args) {
        if (argsAreInvalid(args)) {
            displayHelpText();
            return;
        }

        if (args.length > 1 && args[1].equals(ARG_DEBUG)) {
            Logger.isDebugging = true;
        }

        if (args[0].equals(ARG_FRIEDMAN)) {
            new FriedmanKeyLengthFinder().findKeyLength(CYPHER_TEXT);
        } else if (args[0].equals(ARG_DECRYPT)) {
            // TODO : DO Ex2 a
            Logger.logDebug("Ex2 a : Decrypt!");
        } else {
            displayHelpText();
        }
    }

    private static boolean argsAreInvalid(String[] args) {
        return args == null || args.length == 0 ||  args[0] == null;
    }

    private static void displayHelpText() {
        Logger.logInfo("You need to enter one of the following args :");
        Logger.logInfo(String.format("  %s : Exercise 1.a : Calculate key length using Friedman test", ARG_FRIEDMAN));
        Logger.logInfo(String.format("  %s : Exercise 1.b : Decrypt ciphertext", ARG_DECRYPT));
    }
}
