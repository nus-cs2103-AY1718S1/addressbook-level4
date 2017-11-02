package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author RSJunior37
/**
 * Request MainApp to switch middle panel to Profile
 */
public class SwitchToProfilePanelRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}

