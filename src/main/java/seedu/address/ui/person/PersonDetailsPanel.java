package seedu.address.ui.person;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.UiPart;

/**
 * The panel on the right side of {@link PersonListPanel}. Used to show the details (including photo and all
 * properties) of a specific person (selected on the {@link PersonListPanel}).
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "person/PersonDetailsPanel.fxml";

    private ReadOnlyPerson person;

    @FXML
    private Label name;

    public PersonDetailsPanel(Person person) {
        super(FXML);
        this.person = person;
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
    }

}
