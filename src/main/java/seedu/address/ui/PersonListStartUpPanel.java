package seedu.address.ui;

import javafx.scene.layout.Region;

//@@author jelneo
/**
 * Empty panel to be displayed instead of PersonListPanel before login.
 * Users who have not logged in successfully should not be able to view the contacts in the address book.
 */
public class PersonListStartUpPanel extends UiPart<Region> {

    public static final String PERSON_LIST_START_UP_PANEL_ID = "#personListStartUpPane";

    private static final String FXML = "PersonListStartUpPanel.fxml";

    public PersonListStartUpPanel() {
        super(FXML);
    }
}
