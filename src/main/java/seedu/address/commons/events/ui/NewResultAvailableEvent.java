package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;

    private boolean error;

    public NewResultAvailableEvent(String message) {
        this.message = message;
        this.error = false;
    }

    public NewResultAvailableEvent(String message, boolean error) {
        this.message = message;
        this.error = error;

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public boolean isError() {
        return this.error;
    }

}
