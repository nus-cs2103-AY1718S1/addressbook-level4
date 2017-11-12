package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author deep4k

/**
 * An event requesting to toggle the to ParentMode.
 */
public class ToggleToChildModeEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
