package seedu.address.commons.events.commands;

import seedu.address.commons.events.BaseEvent;

//@@author frozventus
/** Indicates the main contact list is now displayed*/
public class DisplayListResetEvent extends BaseEvent {

    public DisplayListResetEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
