package seedu.address.commons.events.ui;

//@@author jelneo

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that login inputs have changed.
 */
public class LoginInputChangeEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
