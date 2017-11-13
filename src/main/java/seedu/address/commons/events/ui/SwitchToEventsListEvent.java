package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author junyango
/**
 * Represents a change that invokes list switching
 */
public class SwitchToEventsListEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
