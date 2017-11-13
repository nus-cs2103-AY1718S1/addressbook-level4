package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author junming403
/**
 * Indicates a request to view a lesson on the existing lesson list
 */
public class ViewedLessonEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
