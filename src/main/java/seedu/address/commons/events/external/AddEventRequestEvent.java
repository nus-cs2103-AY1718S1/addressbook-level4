package seedu.address.commons.events.external;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.calendarevent.CalendarEvent;

//@@author yilun-zhu

/**
 * Indicates a request for adding an calendar event
 */

public class AddEventRequestEvent extends BaseEvent {

    private CalendarEvent calendarEvent;

    public AddEventRequestEvent(CalendarEvent calendarEvent) {
        this.calendarEvent = calendarEvent;
    }

    public CalendarEvent getCalendarEvent() {
        return calendarEvent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
