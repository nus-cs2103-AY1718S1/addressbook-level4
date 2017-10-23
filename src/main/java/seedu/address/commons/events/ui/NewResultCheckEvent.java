package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 * And checks the validity of the entered command.
 */
public class NewResultCheckEvent extends BaseEvent {

    public final String message;
    public final boolean isError;

    public NewResultCheckEvent(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
