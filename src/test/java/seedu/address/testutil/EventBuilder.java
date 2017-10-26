//@@author A0162268B
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.Title;
import seedu.address.model.event.timeslot.Timeslot;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_TITLE = "Jack's Birthday";
    public static final String DEFAULT_TIMESLOT = "23/10/2017 1900-2100";
    public static final String DEFAULT_DESCRIPTION = "Celebrating Jack's 21st, party all night";

    private Event event;

    public EventBuilder() {
        try {
            Title defaultTitle = new Title(DEFAULT_TITLE);
            Timeslot defaultTimeslot = new Timeslot(DEFAULT_TIMESLOT);
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
            this.event = new Event(defaultTitle, defaultTimeslot, defaultDescription);
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
    public EventBuilder withTitle(String title) {
        try {
            this.event.setTitle(new Title(title));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("title is expected to be unique.");
        }
        return this;
    }


    /**
     * Sets the {@code Timeslot} of the {@code Event} that we are building.
     */
    public EventBuilder withTimeslot(String timeslot) {
        try {
            this.event.setTimeslot(new Timeslot(timeslot));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Timeslot is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        try {
            this.event.setDescription(new Description(description));
        } catch (IllegalValueException ive) {

            throw new IllegalArgumentException("Description is expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
