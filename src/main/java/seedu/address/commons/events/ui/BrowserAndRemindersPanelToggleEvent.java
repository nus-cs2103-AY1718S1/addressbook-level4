package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author justinpoh
/**
 * Represents a toggling between the browser and reminders panels.
 */

public class BrowserAndRemindersPanelToggleEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
