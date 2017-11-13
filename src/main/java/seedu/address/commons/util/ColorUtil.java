package seedu.address.commons.util;

import java.util.HashMap;
import java.util.Random;

//@@author syy94

/**
 * From JavaFX docs, the CSS supports HSB color model instead of HSL color model.
 * <p>
 * HSL is used here as it is easier to randomise a certain color range as compared to
 * RGB.
 *
 * @see <a href="https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html#introlimitations">
 * </a>
 */
public class ColorUtil {
    private static Random random = new Random();
    private static final HashMap<String, String> USED_COLORS = new HashMap<>();

    /**
     * Generate a random Color in HSB format for CSS. This color will then be bounded to the object.
     * Calling this method with the same Object will return the existing color.
     *
     * @param object Object to get Colors for
     * @return String of color in HSB format for CSS. eg. hsb (360, 35%, 50%)
     */
    public static String getUniqueHsbColorForObject(Object object) {
        final String identifier = object.toString();
        if (!USED_COLORS.containsKey(identifier)) {
            USED_COLORS.put(identifier, getTagColor());
        }

        return USED_COLORS.get(identifier);
    }

    public static String getTagColor() {
        return "hsb(" + getHue() + "," + getSaturation() + "%,"
                + getBrightness() + "%)";
    }

    private static int getHue() {
        //0 to 360 degrees. Full spectrum of colors in 5 degrees increments.
        return random.nextInt(72) * 5;
    }

    private static int getSaturation() {
        //60 to 95% Saturation
        return random.nextInt(35) + 60;
    }

    private static int getBrightness() {
        //50 to 75% Brightness
        return random.nextInt(25) + 50;
    }
}
