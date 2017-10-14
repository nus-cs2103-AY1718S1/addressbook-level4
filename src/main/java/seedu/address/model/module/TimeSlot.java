package seedu.address.model.module;

import seedu.address.commons.exceptions.IllegalValueException;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimeSLot(String)}
 */
public class TimeSlot {
    public static final String MESSAGE_TIMESLOT_CONSTRAINTS =
            "Person emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String TIMESLOT_VALIDATION_REGEX = "[\\w]+\\[[\\w]+]";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public TimeSlot(String timeSlot) throws IllegalValueException {
        requireNonNull(timeSlot);
        String trimmedTimeSlot = timeSlot.trim();
        if (!isValidTimeSLot(trimmedTimeSlot)) {
            throw new IllegalValueException(MESSAGE_TIMESLOT_CONSTRAINTS);
        }
        this.value = trimmedTimeSlot;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidTimeSLot(String test) {
        return test.matches(TIMESLOT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeSlot // instanceof handles nulls
                && this.value.equals(((TimeSlot) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
