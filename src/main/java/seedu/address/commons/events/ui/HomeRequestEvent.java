package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author nahtanojmil
/**
 * Indicates a request to display home page
 */
public class HomeRequestEvent extends BaseEvent {


    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

