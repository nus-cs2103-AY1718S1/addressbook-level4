package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event indicates addressbook has changed.
 */
public class RemarkChangedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
