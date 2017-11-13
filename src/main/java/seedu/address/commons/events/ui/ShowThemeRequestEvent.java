package seedu.address.commons.events.ui;

//@@author itsdickson

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to view the themes page.
 */
public class ShowThemeRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author
