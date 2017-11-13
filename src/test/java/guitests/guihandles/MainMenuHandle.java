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
        clickOnMenuItemsSequentially("HELP", "F1");
    }

    /**
     * Opens the {@code HelpWindow} by pressing the shortcut key associated
     * with the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F1);
    }


    /**
     * Opens the {@code ViewAliasWindow} using the menu bar in {@code MainWindow}.
     */
    public void openViewAliasWindowUsingMenu() {
        clickOnMenuItemsSequentially("ALIAS", "F10");
    }

    /**
     * Opens the {@code ViewAliasWindow} by pressing the shortcut key associated
     * with the menu bar in {@code MainWindow}.
     */
    public void openViewAliasWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F10);
    }

    /**
     * Clicks on {@code menuItems} in order.
     */
    private void clickOnMenuItemsSequentially(String... menuItems) {
        Arrays.stream(menuItems).forEach(guiRobot::clickOn);
    }
}
