package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.property.Address;
import seedu.address.model.property.DateTime;
import seedu.address.model.property.Name;
import seedu.address.model.property.PropertyManager;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.model.reminder.Reminder;

//@@author junyango



/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    public static final String DEFAULT_EVENT_NAME = "Hack Your Way 2017";
    public static final String DEFAULT_TIME = "25102010 12:00";
    public static final String DEFAULT_VENUE = "123, Clementi West Ave 6, #08-123";

    private Event event;

    static {
        PropertyManager.initializePropertyManager();
    }

    public EventBuilder() {
        try {
            Name defaultEventName = new Name(DEFAULT_EVENT_NAME);
            DateTime defaultTime = new DateTime(DEFAULT_TIME);
            Address defaultAddress = new Address(DEFAULT_VENUE);
            ArrayList<Reminder> defaultReminder = new ArrayList<>();
            this.event = new Event(defaultEventName, defaultTime, defaultAddress, defaultReminder);
        } catch (IllegalValueException | PropertyNotFoundException ive) {
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
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        try {
            this.event.setName(new Name(name));
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Adds a reminder into the event.
     */
    public EventBuilder withReminder() {
        this.event.getReminders().add(new Reminder(event, event.getTime().toString()));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Event} that we are building.
     */
    public EventBuilder withAddress(String address) {
        try {
            this.event.setAddress(new Address(address));
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Date Time} of the {@code Event} that we are building.
     */
    public EventBuilder withDateTime(String time) {
        try {
            this.event.setDateTime(new DateTime(time));
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("Date and Time are expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
