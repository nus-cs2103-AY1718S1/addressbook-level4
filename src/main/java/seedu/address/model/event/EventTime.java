package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Event's time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class EventTime {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Event date & time must be numbers followed by ddmmyyyy hh:mmpm/am";


        public static final String TIME_VALIDATION_REGEX = "^(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])[0-9]{4}(\\s((0[1-9]|1[012]):([0-5][0-9])(([AaMm|PpMm|]{2,2})))?$)";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public EventTime(String time) throws IllegalValueException {
        requireNonNull(time);
        if (!isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = time;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventTime // instanceof handles nulls
                && this.value.equals(((EventTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

