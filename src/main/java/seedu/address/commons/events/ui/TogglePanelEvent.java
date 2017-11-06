package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

// @@author leonchowwenhao

/**
 * Indicates a request to toggle between event display and browser.
 */
public class TogglePanelEvent extends BaseEvent {

    public TogglePanelEvent() {

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
