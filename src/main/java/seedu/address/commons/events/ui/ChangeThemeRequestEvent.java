package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author khooroko
/**
 * Indicates a request to change theme
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
