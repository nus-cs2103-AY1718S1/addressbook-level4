package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

// @@author LeonChowWenHao

/**
 * Indicates a request to toggle between event display and browser.
 */
public class TogglePanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
