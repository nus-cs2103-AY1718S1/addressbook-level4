package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;
    public final Boolean errorStatus;

    public NewResultAvailableEvent(String message, Boolean error) {
        this.message = message;
        this.errorStatus = error;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
