package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Datetime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Location;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.Title;

//@@author kaiyu92

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_TITLE = "Halloween Horror Night";
    public static final String DEFAULT_DESCRIPTION = "Terrifying Night";
    public static final String DEFAULT_LOCATION = "Univsersal Studio";
    public static final String DEFAULT_DATETIME = "13-10-2017 2359";

    private Event event;

    public EventBuilder() {
        try {
            Title defaultTitle = new Title(DEFAULT_TITLE);
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
            Location defaultLocation = new Location(DEFAULT_LOCATION);
            Datetime defaultDatetime = new Datetime(DEFAULT_DATETIME);
            this.event = new Event(defaultTitle, defaultDescription, defaultLocation, defaultDatetime);
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
     * Sets the {@code title} of the {@code Event} that we are building.
     */
    public EventBuilder withTitle(String title) {
        try {
            this.event.setTitle(new Title(title));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("title is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        try {
            this.event.setDescription(new Description(description));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("description is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String location) {
        try {
            this.event.setLocation(new Location(location));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("location is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code datetime} of the {@code Event} that we are building.
     */
    public EventBuilder withDatetime(String datetime) {
        try {
            this.event.setDatetime(new Datetime(datetime));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("datetime is expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }
}
