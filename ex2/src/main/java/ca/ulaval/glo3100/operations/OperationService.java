package ca.ulaval.glo3100.operations;

import ca.ulaval.glo3100.args.Args;

public class OperationService {

    public static String execute(Args args) {
       return startMode(args);
    }

    private static String startMode(Args args) {
        String result = "";

        switch (args.mode){
            case ECB:
                result = ecb();
                break;
            case CBC:
                result = cbc();
                break;
            case CTR:
                result = ctr();
                break;
            case OFB:
                result = ofb();
                break;
            case CFB:
                result = cfb();
                break;
        }

        return result;
    }

    // TODO : Complete ECB
    private static String ecb() {
        return "";
    }

    // TODO : Complete CBC
    private static String cbc() {
        return "";
    }

    // TODO : Complete CFB
    private static String cfb() {
        return "";
    }

    // TODO : Complete OFB
    private static String ofb() {
        return "";
    }

    // TODO : Complete CTR
    private static String ctr() {
        return "";
    }
}
