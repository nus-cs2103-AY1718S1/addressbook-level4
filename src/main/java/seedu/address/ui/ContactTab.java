package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * An UI component that direct user to {@code Person}.
 *    //@@author yangshuang
 */
public class ContactTab extends UiPart<Region> {

    private static final String FXML = "ContactTab.fxml";

    @FXML
    private AnchorPane contactTab;

    public ContactTab() {
        super(FXML);
    }
}
