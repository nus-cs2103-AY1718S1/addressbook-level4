package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.util.DateUtil.formatDate;
import static seedu.address.model.util.DateUtil.isValidDateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author lawwman
/**
 * Represents the deadline of the debt of a person in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */

public class Deadline {

    public static final String NO_DEADLINE_SET = "No deadline set.";
    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline can only contain input of the format XX-XX-XXXX, taking X as an integer.";
    public final String value;

    /**
     * Validates given Deadline. If no deadline was entered by user, value will read "empty" by
     * default. Else, it will store the date of the deadline.
     *
     * @throws IllegalValueException if given deadline is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (!isValidDeadline(trimmedDeadline)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        } else {
            this.value = formatDate(trimmedDeadline);
        }
    }

    /**
     * Returns true if a given string is a valid person deadline.
     */
    public static boolean isValidDeadline(String test) {
        return isValidDateFormat(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
