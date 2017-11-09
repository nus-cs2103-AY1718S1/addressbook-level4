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

    //@@author bladerail
    /**
     * Opens the {@code UserprofileWindow} using the menu bar in {@code MainWindow}.
     */
    public void openUserProfileWindowUsingMenu() {
        clickOnMenuItemsSequentially("File", "UserProfile");
    }

    //@@author
    /**
     * Opens the {@code HelpWindow} by pressing the shortcut key associated
     * with the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F1);
    }

    //@@author bladerail
    /**
     * Opens the {@code UserProfileWindow} by pressing the shortcut key associated
     * with the menu bar in {@code MainWindow}.
     */
    public void openUserProfileWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F2);
    }

    //@@author
    /**
     * Clicks on {@code menuItems} in order.
     */
    private void clickOnMenuItemsSequentially(String... menuItems) {
        Arrays.stream(menuItems).forEach(guiRobot::clickOn);
    }
}
