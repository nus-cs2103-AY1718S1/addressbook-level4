package seedu.address.commons.util;

import java.util.Random;

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

    public static String getTagColor() {
        return "hsb(" + getHue() + "," + getSaturation() + "%,"
                + getBrightness() + "%)";
    }

    private static int getHue() {
        //full spectrum of colors (in Degrees)
        return random.nextInt(360);
    }

    private static int getSaturation() {
        //60 to 95%
        return random.nextInt(35) + 60;
    }

    private static int getBrightness() {
        //50 to 75%
        return random.nextInt(25) + 50;
    }
}
