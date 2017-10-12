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
     * Clicks the List Button using the menu bar in {@code MainWindow}.
     */
    public void listUsingMenu() {
        clickOnMenuItemsSequentially("Commands", "List");
    }

    /**
     * Clicks the Undo Button using the menu bar in {@code MainWindow}.
     */
    public void undoUsingMenu(){
        clickOnMenuItemsSequentially("Commands", "Undo");
    }

    /**
     * Clicks the Redo Button using the menu bar in {@code MainWindow}.
     */
    public void redoUsingMenu(){
        clickOnMenuItemsSequentially("Commands", "Redo");
    }

    /**
     * Clicks the Clear Button using the menu bar in {@code MainWindow}.
     */
    public void clearUsingMenu(){
        clickOnMenuItemsSequentially("Commands", "Clear");
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
}
