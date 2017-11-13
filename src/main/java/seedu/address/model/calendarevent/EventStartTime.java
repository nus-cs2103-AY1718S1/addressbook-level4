package seedu.address.model.calendarevent;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author yilun-zhu
/**
 * Represents event start time.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class EventStartTime {


    public static final String START_TIME_CONSTRAINTS =
            "Time should be in the format HH:MM, and must be valid. Eg. 09:00";
    public static final String TIME_VALIDATION_REGEX = "^[0-2][0-9]:[0-5][0-9]$";
    public final String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public EventStartTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!isValidTime(trimmedTime)) {
            throw new IllegalValueException(START_TIME_CONSTRAINTS);
        }
        this.value = trimmedTime;
    }

    /**
     * Returns true if a given string is a valid time.
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
                || (other instanceof EventStartDate // instanceof handles nulls
                && this.value.equals(((EventStartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
