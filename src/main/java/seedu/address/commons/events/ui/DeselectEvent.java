package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author ritchielq-reuse
/**
 * Indicates a request to reselect person
 */
public class DeselectEvent extends BaseEvent {


    public DeselectEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
