//@@author erik0704
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Dates;
import seedu.address.model.event.Event;
import seedu.address.model.person.Name;

/**
 * A utility class to help with building Event objects.
 */
public class TaskEventBuilder {
    public static final String DEFAULT_EVENT_NAME = "Doctor appointment";
    public static final String DEFAULT_DATES = "2012-12-23";
    public static final String DEFAULT_DESCRIPTION = "Come early";

    private Event event;

    public TaskEventBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_EVENT_NAME);
            Dates defaultDates = new Dates(DEFAULT_DATES);
            String defaultDes = DEFAULT_DESCRIPTION;
            this.event = new Event(defaultName, defaultDates, defaultDes);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default event's values are invalid.");
        }
    }

    /**
     * Initializes the TaskEventBuilder with the data of {@code eventToCopy}.
     */
    public TaskEventBuilder(Event eventToCopy) {
        this.event = new Event(eventToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public TaskEventBuilder withName(String name) {
        try {
            this.event.setName(new Name(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Dates} of the {@code Event} that we are building.
     */
    public TaskEventBuilder withDates(String date) {
        try {
            this.event.setDate(new Dates(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is invalid");
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public TaskEventBuilder withDescription(String des) {
        this.event.setDescription(des);
        return this;
    }

    public Event build() {
        return this.event;
    }
}
