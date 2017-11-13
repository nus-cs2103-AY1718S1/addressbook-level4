package seedu.room.testutil;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.model.event.Datetime;
import seedu.room.model.event.Description;
import seedu.room.model.event.Event;
import seedu.room.model.event.Location;
import seedu.room.model.event.ReadOnlyEvent;
import seedu.room.model.event.Title;

//@@author sushinoya
/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_TITLE = "USPolymath Session";
    public static final String DEFAULT_LOCATION = "Cinnamon College";
    public static final String DEFAULT_DESCRIPTION = "Conducted by USC";
    public static final String DEFAULT_DATETIME = "05/11/2017 0830 to 2030";

    private Event event;

    public EventBuilder() {
        try {
            Title defaultTitle = new Title(DEFAULT_TITLE);
            Location defaultLocation = new Location(DEFAULT_LOCATION);
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
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
     * Sets the {@code Title} of the {@code Event} that we are building.
     */
    public EventBuilder withTitle(String name) {
        try {
            this.event.setTitle(new Title(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }


    /**
     * Sets the {@code Datetime} of the {@code Event} that we are building.
     */
    public EventBuilder withDatetime(String room) {
        try {
            this.event.setDatetime(new Datetime(room));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("room is expected to be unique.");
        }
        return this;
    }


    /**
     * Sets the {@code Location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String phone) {
        try {
            this.event.setLocation(new Location(phone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }


    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String email) {
        try {
            this.event.setDescription(new Description(email));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
