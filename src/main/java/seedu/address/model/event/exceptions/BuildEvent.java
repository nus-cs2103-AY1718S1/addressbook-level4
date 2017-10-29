package seedu.address.model.event.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

/**
 * A utility class to help with building Event objects.
 */
public class BuildEvent {
    public static final String DEFAULT_NAME = "ZoukOut";
    public static final String DEFAULT_ADDRESS = "Sentosa, Siloso Beach";
    public static final String DEFAULT_DATE = "2018-12-12";

    private Event event;

    public BuildEvent() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Date defaultDate = new Date(DEFAULT_DATE);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);

            this.event = new Event(defaultName, defaultDate, defaultAddress);

        } catch (IllegalValueException ive) {
            throw new AssertionError("Default event's values are invalid.");
        }
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public BuildEvent withName(String name) {
        try {
            this.event.setName(new Name(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Event} that we are building.
     */
    public BuildEvent withAddress(String address) {
        try {
            this.event.setAddress(new Address(address));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Event} that we are building.
     */
    public BuildEvent withDate(String date) {
        try {
            this.event.setDate(new Date(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
