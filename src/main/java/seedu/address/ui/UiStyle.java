package seedu.address.ui;

import java.util.Random;

/**
 * Contains the Styles / Colors that can be used in UI
 */

public class UiStyle {

    private static UiStyle instance = null;

    private static String STYLE_BACKGROUND_COLOR = "-fx-background-color: ";
    private static String HEX_COLOR = "#%1$s";
    // Max hex color "FFFFFF" in integer
    private static int MAX_HEX_COLOR = 16777216;


    private static Random random = new Random();

    public static UiStyle getInstance() {
        if (instance == null) {
            instance = new UiStyle();
        }

        return instance;
    }

    public static String getRandomHexColor() {
        return String.format(HEX_COLOR, Integer.toHexString(random.nextInt(MAX_HEX_COLOR)));
    }

    public static String getSpecificHexColor(String hexString) {
        return String.format(HEX_COLOR, hexString);
    }

    /**
     * @param color either color in String "red,blue,etc.." or hexadecimal color
     * @return String to indicate background color
     */
    public static String getBackgroundStyle(String color) {
        return UiStyle.STYLE_BACKGROUND_COLOR + color;
    }

}
