package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.logging.Logger;

public class PersonDetails extends UiPart<Region> {

    private static final String FXML = "PersonDetails.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonDetails.class);
    public final ReadOnlyPerson person;

    @FXML
    private Label name;

    @FXML
    private Label address;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private Label mrt;

    public PersonDetails(ReadOnlyPerson person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().toString());
        address.setText(person.getAddress().toString());
        phone.setText(person.getPhone().toString());
        email.setText(person.getEmail().toString());
        mrt.setText(person.getMrt().toString());
    }
}
