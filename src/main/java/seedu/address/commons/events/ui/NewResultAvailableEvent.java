package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;
    public final boolean unknownCommandEntered;

    public NewResultAvailableEvent(String message) {
        this.message = message;
        if (message.equals("Unknown command")) {
            unknownCommandEntered = true;
        }
        else {
            unknownCommandEntered = false;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * returns if command entered is valid
     */
    public boolean isUnknownCommandEntered() {
        return unknownCommandEntered;
    }
}
