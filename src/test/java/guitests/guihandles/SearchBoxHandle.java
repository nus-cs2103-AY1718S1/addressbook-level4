package guitests.guihandles;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * A handle to the {@code SearchField} in the GUI.
 */
public class SearchBoxHandle extends NodeHandle<TextField> {

    public static final String SEARCH_FIELD_ID = "#searchField";

    public SearchBoxHandle(TextField searchFieldNode) {
        super(searchFieldNode);
    }

    /**
     * Enters the given Person name in the search field.
     *
     */
    public void run(String toFind) {
        click();
        guiRobot.interact(() -> getRootNode().setText(toFind));
        guiRobot.pauseForHuman();
        guiRobot.type(KeyCode.ENTER);
    }
}
