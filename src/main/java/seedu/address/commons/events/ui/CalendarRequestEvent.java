package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author yilun-zhu
/**
 * Indicates a request for Calendar
 */

public class CalendarRequestEvent extends BaseEvent {

    public CalendarRequestEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
