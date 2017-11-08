package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author junyango

/**
 * Handles switch Theme Event
 */
public class SwitchThemeEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
