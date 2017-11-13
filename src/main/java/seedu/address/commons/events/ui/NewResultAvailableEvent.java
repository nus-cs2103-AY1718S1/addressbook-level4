package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;
    public final boolean isError;

    public NewResultAvailableEvent(String message) {
        this.message = message;
        this.isError = false;
    }
    //@@author limyongsong
    public NewResultAvailableEvent(String message, Boolean isError) {
        this.message = message;
        this.isError = isError;
    }
    //@@author
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
