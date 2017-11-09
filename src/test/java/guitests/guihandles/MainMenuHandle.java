package guitests.guihandles;

import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;

import seedu.address.model.windowsize.WindowSize;

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
        case WindowSize.SMALL_WINDOW_SIZE_INPUT:
            clickOnMenuItemsSequentially("Window", "Small (800x600)");
            break;
        case WindowSize.MEDIUM_WINDOW_SIZE_INPUT:
            clickOnMenuItemsSequentially("Window", "Medium (1024x720)");
            break;
        case WindowSize.BIG_WINDOW_SIZE_INPUT:
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
