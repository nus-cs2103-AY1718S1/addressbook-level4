package seedu.address.commons.events.commands;

import seedu.address.commons.events.BaseEvent;

//@@author frozventus
/** Indicates the Recycle Bin is now displayed*/
public class DisplayBinEvent extends BaseEvent {

    public DisplayBinEvent () {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
