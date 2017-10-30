package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author blaqkrow
/**
 * Indicates that a photo change is occurring.
 */
public class PhotoChangeEvent extends BaseEvent {

    public PhotoChangeEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
