package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Alim95

/**
 * Indicates a request to toggle to ParentMode.
 */
public class ToggleParentChildModeEvent extends BaseEvent {

    public final boolean isSetToParentMode;

    public ToggleParentChildModeEvent(boolean isSetToParentMode) {
        this.isSetToParentMode = isSetToParentMode;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
