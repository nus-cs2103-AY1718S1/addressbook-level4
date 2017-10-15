package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

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
    public static final String DATE_FORMAT = "E',' dd MMM', Year' yyyy";
    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline can only contain input of the format XX-XX-XXXX, taking X as an integer.";
    public static final String DEADLINE_VALIDATION_REGEX = "([0-1][0-9](-)){2}(\\d{4})";
    public final String value;

    /**
     * Validates given Deadline. If no deadline was entered by user, value will read "empty" by
     * default. Else, it will store the date of the deadline.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        if (deadline.equals(NO_DEADLINE_SET)) {
            this.value = deadline;
        } else {
            requireNonNull(deadline);
            String trimmedDeadline = deadline.trim();
            if (!isValidDeadline(trimmedDeadline)) {
                throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
            this.value = trimmedDeadline;
        }
    }

    /**
     * Returns true if a given string is a valid person deadline.
     */
    public static boolean isValidDeadline(String test) {
        return test.matches(DEADLINE_VALIDATION_REGEX);
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

    /**
     *
     * @return formated date value
     */
    private String formatDate() {
        SimpleDateFormat ft = new SimpleDateFormat(DATE_FORMAT);
        int year = Integer.parseInt(value.substring(6, 10));
        int day = Integer.parseInt(value.substring(0, 2));
        int month = Integer.parseInt(value.substring(3, 5)) - 1;
        Date date = new GregorianCalendar(year, month, day).getTime();
        return ft.format(date);
    }
}
