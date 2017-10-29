package guitests.guihandles;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * A handle to the {@code SearchBox} in the GUI.
 */
public class SearchBoxHandle extends NodeHandle<TextField> {

    public static final String SEARCH_FIELD_ID = "#searchBox";

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

    /**
     * Returns the style of search box.
     */
    public String getStyle() {
        return getRootNode().getStyle();
    }
}
