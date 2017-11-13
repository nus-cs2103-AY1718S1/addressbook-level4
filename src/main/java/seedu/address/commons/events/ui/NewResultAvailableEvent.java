package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;
    private boolean isErrorCommand = false;

    /* Used to populate the Alias window */
    public NewResultAvailableEvent(String message) {
        this.message = message;
        if (message.equals("Unknown command") || (message.length() >= 8 && message.substring(0, 7).equals("Invalid"))) {
            isErrorCommand = true;
        }
    }

    public boolean getIsErrorCommand() {
        return isErrorCommand;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
