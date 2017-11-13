package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.event.Desc;
import seedu.address.model.person.event.Event;
import seedu.address.model.person.event.EventDate;
import seedu.address.model.person.event.Header;
import seedu.address.model.person.event.ReadOnlyEvent;

//@@author royceljh
/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_HEADER = "Meeting";
    public static final String DEFAULT_DESC = "location";
    public static final String DEFAULT_EVENT_DATE = "2017-12-20";

    private Event event;

    public EventBuilder() {
        try {
            Header defaultHeader = new Header(DEFAULT_HEADER);
            Desc defaultDesc = new Desc(DEFAULT_DESC);
            EventDate defaultEventDate = new EventDate(DEFAULT_EVENT_DATE);
            this.event = new Event(defaultHeader, defaultDesc, defaultEventDate);
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
     * Sets the {@code Header} of the {@code Event} that we are building.
     */
    public EventBuilder withHeader(String header) {
        try {
            this.event.setHeader(new Header(header));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("header is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Desc} of the {@code Event} that we are building.
     */
    public EventBuilder withDesc(String desc) {
        try {
            this.event.setDesc(new Desc(desc));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("description is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code EventDate} of the {@code Event} that we are building.
     */
    public EventBuilder withEventDate(String eventDate) {
        try {
            this.event.setEventDate(new EventDate(eventDate));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("eventDate is expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
