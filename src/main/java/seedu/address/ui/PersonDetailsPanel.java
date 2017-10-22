package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The panel on the right side of {@link PersonListPanel}. Used to show the details (including photo and all
 * properties) of a specific person (selected on the {@link PersonListPanel}).
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";

    private ReadOnlyPerson person;

    @FXML
    private Label name;

    public PersonDetailsPanel(Person person) {
        super(FXML);
        this.person = person;
    }
}
