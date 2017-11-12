//@@author Jemereny
package seedu.address.ui;

import java.util.Random;

import javafx.scene.Scene;

/**
 * Contains the Styles / Colors that can be used in UI
 */

public class UiStyle {

    private static UiStyle instance = null;

    private static final String LIGHT_THEME_STYLE = "view/LightTheme.css";
    private static final String DARK_THEME_STYLE = "view/DarkTheme.css";

    private static Scene scene = null;

    private static final String STYLE_BACKGROUND_COLOR = "-fx-background-color: ";
    private static final String HEX_COLOR = "#%1$s";
    // Max hex color "FFFFFF" in integer
    private static final int MAX_HEX_COLOR = 16777216;

    private static Random random = new Random();

    public static UiStyle getInstance() {
        if (instance == null) {
            instance = new UiStyle();
        }

        return instance;
    }

    public static void setScene(Scene s) {
        scene = s;
        setToDarkTheme();
    }

    public static String getRandomHexColor() {
        return String.format(HEX_COLOR, Integer.toHexString(random.nextInt(MAX_HEX_COLOR)));
    }

    public static String getSpecificHexColor(String hexString) {
        return String.format(HEX_COLOR, hexString);
    }

    /**
     * @param color in hexadecimals
     * @return String to indicate background color
     */
    public static String getBackgroundStyle(String color) {
        return UiStyle.STYLE_BACKGROUND_COLOR + color;
    }

    //---------------------------------------------------------
    public static void setToLightTheme() {
        scene.getStylesheets().remove(DARK_THEME_STYLE);
        scene.getStylesheets().add(LIGHT_THEME_STYLE);
    }

    public static void setToDarkTheme() {
        scene.getStylesheets().remove(LIGHT_THEME_STYLE);
        scene.getStylesheets().add(DARK_THEME_STYLE);
    }

}
