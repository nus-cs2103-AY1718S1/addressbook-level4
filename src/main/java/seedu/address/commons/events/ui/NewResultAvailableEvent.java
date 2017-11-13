package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;
    public final boolean invalid;

    public NewResultAvailableEvent(String message, boolean invalid) {
        this.message = message;
        this.invalid = invalid;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
