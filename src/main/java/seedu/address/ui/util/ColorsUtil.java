package seedu.address.ui.util;

/**
 * A utility class for colors used in UI.
 */
public class ColorsUtil {
    public static final String RED = "#d06651";
    public static final String YELLOW = "#f1c40f";
    public static final String BLUE = "#3498db";
    public static final String TEAL = "#1abc9c";
    public static final String GREEN = "#2ecc71";
    public static final String PURPLE = "#9b59b6";

    private ColorsUtil() {} // prevents instantiation

    public static String[] getTagColors() {
        return new String[] { RED, YELLOW, BLUE, TEAL, GREEN, PURPLE };
    }
}
