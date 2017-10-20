package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Event's time in the address book.
 */

public class EventTime {


    public static final String MESSAGE_EVENT_TIME_CONSTRAINTS =
            "Event description should not be blank";

    /*
     * The first character of the event name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String EVNET_TIME_VALIDATION_REGEX = "^(0[1-9]|[12][\\d]|3[01]|[1-9])[///./-]"
            + "(0[1-9]|1[012]|[1-9])[///./-](19|20)\\d\\d$";

    public final String eventTime;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public EventTime(String desc) throws IllegalValueException {
        requireNonNull(desc);
        String trimmedName = desc.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_EVENT_TIME_CONSTRAINTS);
        }
        this.eventTime = trimmedName;
    }

    /**
     * Returns true if a given string is a valid event description.
     */
    public static boolean isValidName(String test) {
        return test.matches(EVNET_TIME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return eventTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.event.EventTime // instanceof handles nulls
                && this.eventTime.equals(((seedu.address.model.event.EventTime) other).eventTime)); // state check
    }

    @Override
    public int hashCode() {
        return eventTime.hashCode();
    }

}
