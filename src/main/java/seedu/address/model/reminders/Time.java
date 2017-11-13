package seedu.address.model.reminders;

import static java.util.Objects.requireNonNull;

import java.time.LocalTime;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author justinpoh
/**
 * Represents a Reminder's time in the program.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time must be in 24-hour format,"
            + " with a colon separating the hour and minute fields.\n"
            + "Example: 09:00, 23:59, 17:56";

    public static final String HOUR_MIN_SEPARATOR = ":";

    public static final int TIME_HOUR_INDEX = 0;
    public static final int TIME_MIN_INDEX = 1;

    private static final String TIME_VALIDATION_REGEX = "(0[0-9]|1[0-9]|2[0-3]):"
            + "(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])";

    public final String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String time) throws IllegalValueException {
        requireNonNull(time);
        if (!isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }

        this.value = time;
    }

    /**
     * Returns true if a given string is a valid reminder time.
     */
    public static boolean isValidTime(String time) {
        if (!time.matches(TIME_VALIDATION_REGEX)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the time as a LocalTime object for easy comparison of chronology between reminders.
     */
    public LocalTime toLocalTime() {
        String[] splitTime = value.split(HOUR_MIN_SEPARATOR);
        final int hour = Integer.parseInt(splitTime[TIME_HOUR_INDEX]);
        final int min = Integer.parseInt(splitTime[TIME_MIN_INDEX]);
        return LocalTime.of(hour, min);
    }


    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Time
                && this.value.equals(((Time) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
