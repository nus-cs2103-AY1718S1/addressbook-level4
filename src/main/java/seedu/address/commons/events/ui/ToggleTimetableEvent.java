package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author reginleiff
/**
 * An event requesting to toggle the timetable view.
 */
public class ToggleTimetableEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
