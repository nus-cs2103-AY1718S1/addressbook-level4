package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

// @@author LeonChowWenHao

/**
 * Indicates that a select command has been entered and a request to toggle browser to the front.
 */
public class ToggleSelectEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
