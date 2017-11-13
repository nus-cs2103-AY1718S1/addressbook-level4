package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author darrinloh
/**
 *  Indicates a request to change the theme of OneBook
 */

public class ModeChangeRequestEvent extends BaseEvent {
    public ModeChangeRequestEvent()  {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}


