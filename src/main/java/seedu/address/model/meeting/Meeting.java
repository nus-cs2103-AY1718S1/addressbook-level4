package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Meeting in the address book.
 * Guarantees: immutable
 */
public class Meeting {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time format should be YYYY-MM-DD HH-MM";

    public final LocalDateTime date;
    public final String value;

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Meeting(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime date = LocalDateTime.parse(trimmedTime, formatter);
            this.date = date;
            value = date.format(formatter);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Meeting // instanceof handles nulls
                && this.date.equals(((Meeting) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + value + "]";
    }

}
