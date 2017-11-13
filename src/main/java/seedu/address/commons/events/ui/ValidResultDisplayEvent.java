package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Alim95

/**
 * Indicates that a valid command is entered.
 */
public class ValidResultDisplayEvent extends BaseEvent {

    public final String message;

    public ValidResultDisplayEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
