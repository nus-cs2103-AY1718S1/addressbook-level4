package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Alim95

/**
 * An event requesting to update the pinned panel.
 */
public class UpdatePinnedPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
