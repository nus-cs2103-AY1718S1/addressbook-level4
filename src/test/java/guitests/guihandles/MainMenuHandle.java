package guitests.guihandles;

import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends NodeHandle<Node> {
    public static final String MENU_BAR_ID = "#menuBar";

    public MainMenuHandle(Node mainMenuNode) {
        super(mainMenuNode);
    }

    /**
     * Opens the {@code HelpWindow} using the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingMenu() {
        clickOnMenuItemsSequentially("Help", "F1");
    }


    /**
     * Opens the {@code HelpWindow} by pressing the shortcut key associated
     * with the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F1);
    }

    /**
     * Clicks on {@code menuItems} in order.
     */
    private void clickOnMenuItemsSequentially(String... menuItems) {
        Arrays.stream(menuItems).forEach(guiRobot::clickOn);
    }

    /**
     * Changes to the dark theme using the menu bar in {@code MainWindow}.
     */
    public void changeToDarkThemeUsingMenu() {
        clickOnMenuItemsSequentially("Theme", "Dark");
    }

    /**
     * Changes to the light theme using the menu bar in {@code MainWindow}.
     */
    public void changeToLightThemeUsingMenu() {
        clickOnMenuItemsSequentially("Theme", "Light");
    }

    /**
     * Changes to the red theme using the menu bar in {@code MainWindow}.
     */
    public void changeToRedThemeUsingMenu() {
        clickOnMenuItemsSequentially("Theme", "Red");
    }

    /**
     * Changes to the red theme using the menu bar in {@code MainWindow}.
     */
    public void changeToBlueThemeUsingMenu() {
        clickOnMenuItemsSequentially("Theme", "Blue");
    }

    /**
     * Changes to the red theme using the menu bar in {@code MainWindow}.
     */
    public void changeToGreenThemeUsingMenu() {
        clickOnMenuItemsSequentially("Theme", "Green");
    }

}
