package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.schedule.ReadOnlySchedule;


/**
 * An UI component that displays information of a {@code Schedule}.
 */
public class ScheduleCard extends UiPart<Region> {

    private static final String FXML = "ScheduleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlySchedule schedule;

    @FXML
    private Label scheduleName;
    @FXML
    private Label scheduleId;

    public ScheduleCard(ReadOnlySchedule schedule, int displayedIndex) {
        super(FXML);
        this.schedule = schedule;
        scheduleId.setText(displayedIndex + ". ");
        bindListeners(schedule);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Schedule} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlySchedule schedule) {
        scheduleName.textProperty().bind(Bindings.convert(schedule.nameProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCard)) {
            return false;
        }

        // state check
        ScheduleCard card = (ScheduleCard) other;
        return scheduleId.getText().equals(card.scheduleId.getText())
                && schedule.equals(card.schedule);
    }
}
