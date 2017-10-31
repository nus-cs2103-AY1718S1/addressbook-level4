package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;

import javafx.scene.Scene;
import seedu.address.logic.commands.ThemeCommand;

/**
 * A singleton class that manages the changing of scene graph's stylesheets at runtime.
 */
public class UiTheme {
    public static final String THEME_DAY = "view/ThemeDay.css";
    public static final String THEME_NIGHT = "view/ThemeNight.css";
    public static final String THEME_DAY_EXTENSIONS = "view/ThemeDayExtensions.css";
    public static final String THEME_NIGHT_EXTENSIONS = "view/ThemeNightExtensions.css";

    private static UiTheme uiTheme = new UiTheme();
    private Scene scene;
    private BrowserPanel browserPanel;

    private UiTheme() {
        // Prevents any other class from instantiating
    }

    /**
     * @return instance of UiTheme
     */
    public static UiTheme getInstance() {
        return uiTheme;
    }

    /**
     * Sets the root scene graph obtained from MainWindow.
     * @param scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setBrowserPanel(BrowserPanel browserPanel) {
        this.browserPanel = browserPanel;
    }

    /**
     * Changes the theme based on user input and
     * loads the corresponding default html page.
     * @param option
     */
    public void changeTheme(String option) {
        scene.getStylesheets().clear();

        if (option.equals(PREFIX_OPTION + ThemeCommand.COMMAND_OPTION_DAY)) {
            scene.getStylesheets().setAll(THEME_DAY, THEME_DAY_EXTENSIONS);
            browserPanel.loadDefaultPage(scene);
        } else {
            scene.getStylesheets().setAll(THEME_NIGHT, THEME_NIGHT_EXTENSIONS);
            browserPanel.loadDefaultPage(scene);
        }
    }
}
