package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Request MainApp to switch from Insurance Profile to Person Profile
 */
public class SwitchPanelRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}

