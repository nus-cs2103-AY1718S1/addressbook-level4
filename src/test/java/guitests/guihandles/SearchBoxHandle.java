package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

/**
 * A handle to the {@code SearchBox} in the GUI.
 */
public class SearchBoxHandle extends NodeHandle<TextField> {

    public static final String COMMAND_INPUT_FIELD_ID = "#searchTextField";

    public SearchBoxHandle(TextField searchBoxNode) {
        super(searchBoxNode);
    }

    /**
     * Returns the text in the search box.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     * @return true because search box never fails.
     */
    public boolean run(String command) {
        click();
        guiRobot.interact(() -> getRootNode().setText(command));
        guiRobot.pauseForHuman();

        return true;
    }

    /**
     * Returns the list of style classes present in the command box.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
