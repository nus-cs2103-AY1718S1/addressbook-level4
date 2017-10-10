package seedu.address.ui;



import java.util.logging.Logger;

import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;

//@@author jelneo
/**
 * Empty panel to be displayed instead of PersonListPanel before login.
 * Users who have not logged in successfully should not be able to view the contacts in the address book.
 */
public class PersonListStartUpPanel extends UiPart<Region>{

    private static final String FXML = "PersonListStartUpPanel.fxml";

    public PersonListStartUpPanel() {
        super(FXML);
    }
}
