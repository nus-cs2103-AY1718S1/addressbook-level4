package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;
    public final boolean hasError;

    public NewResultAvailableEvent(String message, boolean hasError) {
        this.message = message;
        this.hasError = hasError;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
