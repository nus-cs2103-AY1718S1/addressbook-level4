package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.ScheduleCard;

/**
 * Represents a selection change in the Person List Panel
 */
public class SchedulePanelSelectionChangedEvent extends BaseEvent {

    private final ScheduleCard newSelection;

    public SchedulePanelSelectionChangedEvent(ScheduleCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ScheduleCard getNewSelection() {
        return newSelection;
    }
}
