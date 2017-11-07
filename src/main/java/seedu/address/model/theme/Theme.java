package seedu.address.model.theme;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import javafx.scene.Scene;
import javafx.stage.Stage;

//@@author ChenXiaoman
/**
 * Represent a theme of an application
 * Guarantees: immutable; name is valid as declared in {@link #isValidThemeName(String)}
 */
public class Theme {

    public static final String DARK_THEME = "dark";
    public static final String BRIGHT_THEME = "bright";
    public static final String[] ALL_THEME_NAMES = {DARK_THEME, BRIGHT_THEME};
    public static final String DARK_THEME_CSS_FILE_NAME = "view/DarkTheme.css";
    public static final String BRIGHT_THEME_CSS_FILE_NAME = "view/BrightTheme.css";

    private static String currentTheme;

    /**
     * Change the current theme
     */
    public static void setCurrentTheme(String currentTheme) {
        requireNonNull(currentTheme);
        assert (isValidThemeName(currentTheme));
        if (isValidThemeName(currentTheme)) {
            Theme.currentTheme = currentTheme;
        }
    }

    /**
     * Get the current theme
     */
    public static String getCurrentTheme() {
        return currentTheme;
    }

    /**
     * Returns true if a given string is a valid theme name.
     */
    public static boolean isValidThemeName(String test) {
        return Arrays.asList(ALL_THEME_NAMES).contains(test);
    }

    /**
     * Change the theme to the given theme name
     * @param primaryStage
     * @param newTheme
     */
    public static void changeTheme(Stage primaryStage, String newTheme) {
        isValidThemeName(currentTheme);

        if (isValidThemeName(newTheme)) {
            Scene scene = primaryStage.getScene();

            // Clear the original theme
            scene.getStylesheets().clear();

            // Add new theme to scene
            String cssFileName = null;

            // Get the associate CSS file path for theme
            switch (newTheme) {
            case DARK_THEME:
                cssFileName = DARK_THEME_CSS_FILE_NAME;
                break;
            case BRIGHT_THEME:
                cssFileName = BRIGHT_THEME_CSS_FILE_NAME;
                break;
            default:
                cssFileName = DARK_THEME_CSS_FILE_NAME;
                break;
            }

            // Set the theme to scene
            scene.getStylesheets().add(cssFileName);
            primaryStage.setScene(scene);
        }
    }
}
