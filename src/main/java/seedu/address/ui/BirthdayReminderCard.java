package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

//@@author justinpoh
/**
 * An UI component that displays the name, nickname and birthday of a Person.
 */
public class BirthdayReminderCard extends UiPart<Region> {

    private static final String FXML = "BirthdayReminderCard.fxml";

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label nickname;
    @FXML
    private Label birthday;
    @FXML
    private Label icon;

    public BirthdayReminderCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        bindListeners(person);
        initIcon();
    }


    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        nickname.textProperty().bind(Bindings.convert(person.nicknameProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
    }

    /**
     * Initiates the appropriate icon depending on {@code person}'s birthday.
     */
    private void initIcon() {
        if (person.getBirthday().isBirthdayToday() || person.getBirthday().isBirthdayTomorrow()) {
            icon.setVisible(true);
        } else {
            icon.setVisible(false);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BirthdayReminderCard)) {
            return false;
        }

        // state check
        BirthdayReminderCard card = (BirthdayReminderCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
