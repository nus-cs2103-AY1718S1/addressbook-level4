package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/** Indicates the Browser Panel should be swapped in */
public class ToggleBrowserPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

