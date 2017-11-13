package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;
    //@@author derickjw
    public final boolean isInvalid;

    public NewResultAvailableEvent(String message, boolean isInvalid) {
        this.message = message;
        this.isInvalid = isInvalid;
    }
    //@@author
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
