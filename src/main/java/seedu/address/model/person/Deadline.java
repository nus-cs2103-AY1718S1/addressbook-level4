package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.util.DateUtil.compareDates;
import static seedu.address.model.util.DateUtil.convertStringToDate;
import static seedu.address.model.util.DateUtil.formatDate;
import static seedu.address.model.util.DateUtil.isValidDateFormat;

import java.util.Date;

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
    public final String value; // format of DD-MM-YYYY.
    public final String valueToDisplay; // format of DAY, DD MM, 'Year' YYYY.

    /**
     * Validates given Deadline. If no deadline was entered by user, value will read "empty" by
     * default. Else, it will store the date of the deadline.
     *
     * @throws IllegalValueException if given deadline is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (trimmedDeadline.equals(NO_DEADLINE_SET)) {
            this.value = this.valueToDisplay = trimmedDeadline;
        } else {
            if (!isValidDeadline(trimmedDeadline)) {
                throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
            this.value = trimmedDeadline;
            this.valueToDisplay = formatDate(trimmedDeadline);
        }
    }

    /**
     * Validates if deadline created is before date borrowed.
     */
    public void checkDateBorrow(Date dateBorrow) throws IllegalValueException {
        if (valueToDisplay.equals(NO_DEADLINE_SET)) {
            return;
        } else if (!compareDates(dateBorrow, convertStringToDate(valueToDisplay))) {
            throw new IllegalValueException("Deadline cannot be before Date borrowed");
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
        return valueToDisplay;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    /**
     * Compares two {@code Deadline} objects and returns 1 if second date is earlier, -1 if later or same.
     * No deadline set will default to be later than any given deadline.
     * @param other the {@code Deadline} object to compare to.
     * @return an integer value of 1 if second date is earlier, -1 if second date is later or same, 0 if both deadlines
     * have not been set.
     */
    public int compareTo(Deadline other) {
        if (this.valueToDisplay.equals(NO_DEADLINE_SET)) {
            if (other.valueToDisplay.equals(NO_DEADLINE_SET)) {
                return 0;
            } else {
                return 1;
            }
        } else if (other.valueToDisplay.equals(NO_DEADLINE_SET)
                || compareDates(convertStringToDate(this.valueToDisplay), convertStringToDate(other.valueToDisplay))) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
