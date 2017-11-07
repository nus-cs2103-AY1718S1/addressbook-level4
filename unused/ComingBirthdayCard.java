//@@author yanji1221
package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of coming birthdays in certain month.
 */
public class ComingBirthdayCard extends UiPart<Region> {

    private static final String FXML = "ComingBirthdayz.fxml";

    public final ReadOnlyPerson person;

    @FXML
    private HBox birthdayListCardPane;
    @FXML
    private Label id;
    @FXML
    private Label birthday;
    @FXML
    private Label name;

    public ComingBirthdayCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ComingBirthdayCard)) {
            return false;
        }

        // state check
        ComingBirthdayCard comingBirthday = (ComingBirthdayCard) other;
        return id.getText().equals(comingBirthday.id.getText())
                && person.equals(comingBirthday.person);
    }

}
