package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "First project meeting";
    public static final String DEFAULT_DESCRIPTION = "Discuss coding";
    public static final String DEFAULT_TIME = "01/10/2018";
    private Event event;

    public EventBuilder() {
        try {
            EventName defaultName = new EventName(DEFAULT_NAME);
            EventDescription defaultDescription = new EventDescription(DEFAULT_DESCRIPTION);
            EventTime defaultTime = new EventTime(DEFAULT_TIME);

            this.event = new Event(defaultName, defaultDescription, defaultTime);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default event's values are invalid.");
        }
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(ReadOnlyEvent eventToCopy) {
        this.event = new Event(eventToCopy);
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        try {
            this.event.setEventName(new EventName(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Impossible.");
        }
        return this;
    }

    /**
     * Sets the {@code EventDescription} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        try {
            this.event.setEventDescription(new EventDescription(description));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Impossible.");
        }
        return this;
    }

    /**
     * Sets the {@code EventTime} of the {@code Event} that we are building.
     */
    public EventBuilder withTime(String time) {
        try {
            this.event.setETime(new EventTime(time));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Impossible.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
