package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.ReminderCard;

/**
 * Represents a selection change in the Reminder List Panel
 */
public class ReminderPanelSelectionChangedEvent extends BaseEvent {


    private final ReminderCard newSelection;

    public ReminderPanelSelectionChangedEvent(ReminderCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReminderCard getNewSelection() {
        return newSelection;
    }
}
