package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Create an event to handle the updates of meeting list when persons are handled
 */
public class UpdateChangeEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
