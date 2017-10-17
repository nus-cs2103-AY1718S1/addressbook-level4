package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * An UI component that direct users to {@code Event}.
 *    //@@author yangshuang
 */
public class EventTab extends UiPart<Region> {

    private static final String FXML = "EventTab.fxml";

    @FXML
    private AnchorPane eventTab;

    public EventTab() {
        super(FXML);
    }
}
