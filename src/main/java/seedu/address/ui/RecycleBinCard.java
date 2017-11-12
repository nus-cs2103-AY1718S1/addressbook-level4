package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

//@@author Pengyuz
/**
 * An UI component that displays information of a person in the recycle bin.
 */
public class RecycleBinCard extends UiPart<Region> {

    private static final String FXML = "RecycleBinCard.fxml";

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label p;
    @FXML
    private Label e;


    public RecycleBinCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        p.setText("     p:");
        e.setText("     e:");
        bindListeners(person);
    }


    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecycleBinCard)) {
            return false;
        }

        // state check
        RecycleBinCard card = (RecycleBinCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
