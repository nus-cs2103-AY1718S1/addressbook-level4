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

    public static final String DEFAULT_PAGE = "default.html";
    private static final String FXML = "PersonListStartUpPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListStartUpPanel.class);

    public PersonListStartUpPanel() {
        super(FXML);
    }
}
