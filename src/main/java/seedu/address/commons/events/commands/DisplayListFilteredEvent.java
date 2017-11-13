package seedu.address.commons.events.commands;

import seedu.address.commons.events.BaseEvent;

//@@author frozventus
/** Indicates the the displayed list is now filtered*/
public class DisplayListFilteredEvent extends BaseEvent {

    public DisplayListFilteredEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
