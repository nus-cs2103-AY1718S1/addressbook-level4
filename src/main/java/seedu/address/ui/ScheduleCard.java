package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Region;
import seedu.address.model.schedule.Schedule;

/**
 * An UI component that displays information of a {@code Schedule}.
 */
public class ScheduleCard extends UiPart<Region> {
    private static final String FXML = "ScheduleCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Schedule schedule;

    @FXML
    private HBox cardPane;
    @FXML
    private Label activity;
    @FXML
    private Label number;
    @FXML
    private Label date;
    @FXML
    private Label personName;

    public ScheduleCard(Schedule schedule, int displayedIndex) {
        super(FXML);
        this.schedule = schedule;
        number.setText(displayedIndex + ". ");
        bindListeners(schedule);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Schedule schedule) {
        activity.textProperty().bind(Bindings.convert(schedule.getActivityProperty()));
        date.textProperty().bind(Bindings.convert(schedule.getScheduleDateProperty()));
        personName.textProperty().bind(Bindings.convert(schedule.getPersonInvolved().nameProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        ScheduleCard card = (ScheduleCard) other;
        return number.getText().equals(card.number.getText())
                && schedule.equals(card.schedule);
    }
}
