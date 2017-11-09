package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author jelneo
/**
 * Indicates a request to display login text fields
 */
public class ChangeToLoginViewEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
