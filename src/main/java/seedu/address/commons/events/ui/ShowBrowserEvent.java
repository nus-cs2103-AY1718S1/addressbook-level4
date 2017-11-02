package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request show the browser panel
 */
public class ShowBrowserEvent extends BaseEvent {

    public ShowBrowserEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
