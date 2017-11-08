package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * To raise a ClearBrowserPanel Event
 */
public class ClearBrowserPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
