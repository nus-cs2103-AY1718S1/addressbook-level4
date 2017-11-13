package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.reminders.ReadOnlyReminder;

//@@author justinpoh
/**
 * An UI component that displays the content, date, time and status of a Reminder.
 */
public class ReminderCard extends UiPart<Region> {
    private static final String FXML = "ReminderCard.fxml";
    public final ReadOnlyReminder source;

    @FXML
    private HBox cardPane;
    @FXML
    private Label reminder;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label status;

    public ReminderCard(ReadOnlyReminder reminder, int displayedIndex) {
        super(FXML);
        source = reminder;
        id.setText(displayedIndex + ". ");
        bindListeners(reminder);
    }


    public boolean isEventToday() {
        return source.isEventToday();
    }

    public boolean isEventWithinThreeDays() {
        return source.isEventWithinThreeDays();
    }

    public boolean hasEventPassed() {
        return source.hasEventPassed();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Reminder} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyReminder source) {
        reminder.textProperty().bind(Bindings.convert(source.reminderProperty()));
        date.textProperty().bind(Bindings.convert(source.dateProperty()));
        time.textProperty().bind(Bindings.convert(source.timeProperty()));
        status.textProperty().bind(Bindings.convert(source.statusProperty()));
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && source.equals(card.source);
    }
}
