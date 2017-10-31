package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Alim95

/**
 * An event requesting to toggle the viewo to PersonPanel.
 */
public class ToggleToPersonViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
