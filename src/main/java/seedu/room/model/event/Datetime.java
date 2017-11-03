package seedu.room.model.event;

import static java.util.Objects.requireNonNull;

import seedu.room.commons.exceptions.IllegalValueException;

//@@author sushinoya
/**
 * Represents a Event's Datetime in the event book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDatetime(String)}
 */
public class Datetime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Event datetime should only contain dd-mm-yyyy hhmm";
    /*
     * The first character of the datetime must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATETIME_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given datetime.
     *
     * @throws IllegalValueException if given datetime string is invalid.
     */
    public Datetime(String datetime) throws IllegalValueException {
        requireNonNull(datetime);
        if (!isValidDatetime(datetime)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.value = datetime;
    }

    /**
     * Returns true if a given string is a valid event datetime.
     */
    public static boolean isValidDatetime(String test) {
        return test.matches(DATETIME_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Datetime // instanceof handles nulls
                && this.value.equals(((Datetime) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
