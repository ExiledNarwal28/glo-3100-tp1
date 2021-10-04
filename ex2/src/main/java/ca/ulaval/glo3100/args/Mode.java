package ca.ulaval.glo3100.args;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Mode {
    ECB("ECB"),
    CBC("CBC"),
    CTR("CTR"),
    OFB("OFB"),
    CFB("CFB");

    private final String mode;
    private static final Map<String, Mode> lookup = new HashMap<>();

    static {
        for (Mode mode : Mode.values()) {
            lookup.put(mode.toString(), mode);
        }
    }

    Mode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return mode;
    }

    public static Mode get(String mode) {
        if (mode == null) throwInvalidMode();

        Mode foundMode = lookup.get(mode.toUpperCase());

        if (foundMode == null) throwInvalidMode();

        return foundMode;
    }

    private static void throwInvalidMode() {
        throw new IllegalArgumentException("Invalid mode");
    }
}
