package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request show the meeting panel
 */
public class ShowMeetingEvent extends BaseEvent {

    public ShowMeetingEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}