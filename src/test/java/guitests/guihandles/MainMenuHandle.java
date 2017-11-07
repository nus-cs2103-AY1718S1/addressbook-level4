package guitests.guihandles;

import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;

import seedu.address.logic.commands.ChangeWindowSizeCommand;

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

    //@@author vivekscl
    /**
     * Clicks on the window sizes using the menu bar in {@code MainWindow}.
     */
    public void clickOnWindowSizesUsingMenu(String windowSize) {
        switch(windowSize) {
        case ChangeWindowSizeCommand.SMALL_WINDOW_SIZE_PARAM:
            clickOnMenuItemsSequentially("Window", "Small (800x600)");
            break;
        case ChangeWindowSizeCommand.MEDIUM_WINDOW_SIZE_PARAM:
            clickOnMenuItemsSequentially("Window", "Medium (1024x720)");
            break;
        case ChangeWindowSizeCommand.BIG_WINDOW_SIZE_PARAM:
            clickOnMenuItemsSequentially("Window", "Big (1600x1024)");
            break;
        default:
            assert false : "Invalid window size provided";
            break;
        }

    }

    //@@author
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
}
