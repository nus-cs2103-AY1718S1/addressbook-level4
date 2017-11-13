package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Eric
/**
 * Indicates a request to change calendar view
 */
public class CalendarViewEvent extends BaseEvent {

    public final Character c;

    public CalendarViewEvent(Character c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
