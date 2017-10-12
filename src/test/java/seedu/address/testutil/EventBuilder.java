package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.EventVenue;
import seedu.address.model.event.ReadOnlyEvent;


/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_EVENT_NAME = "Hack Your Way 2017";
    public static final String DEFAULT_TIME = "25102010 12:00am";
    public static final String DEFAULT_VENUE = "123, Clementi West Ave 6, #08-123";

    private Event event;

    public EventBuilder() {
        try {
            EventName defaultEventName = new EventName(DEFAULT_EVENT_NAME);
            EventTime defaultTime = new EventTime(DEFAULT_TIME);
            EventVenue defaultVenue = new EventVenue(DEFAULT_VENUE);
            this.event = new Event(defaultEventName, defaultTime, defaultVenue);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default event's values are invalid.");
        }
    }

    /**
     * Initializes the EventBuilder with the data of {@code EventToCopy}.
     */
    public EventBuilder(ReadOnlyEvent eventToCopy) {
        this.event = new Event(eventToCopy);
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withEventName(String EventName) {
        try {
            this.event.setName(new EventName(EventName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }
    /**
     * Sets the {@code Address} of the {@code Event} that we are building.
     */
    public EventBuilder withVenue(String address) {
        try {
            this.event.setVenue(new EventVenue(address));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("venue is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Date Time} of the {@code Event} that we are building.
     */
    public EventBuilder withDateTime(String time) {
        try {
            this.event.setDateTime(new EventTime(time));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Date and Time are expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
