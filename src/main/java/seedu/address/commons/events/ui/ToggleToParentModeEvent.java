package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Alim95

/**
 * An event requesting to toggle the to ParentMode.
 */
public class ToggleToParentModeEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
