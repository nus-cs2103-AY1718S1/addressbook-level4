package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;
    public final boolean isError;
    //@@author yanji1221
    public NewResultAvailableEvent(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }
    //@@author
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
