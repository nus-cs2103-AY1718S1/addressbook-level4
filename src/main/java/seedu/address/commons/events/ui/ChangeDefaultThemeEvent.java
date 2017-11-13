package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author ZhangH795

/**
 * Indicates a request for App termination
 */
public class ChangeDefaultThemeEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
