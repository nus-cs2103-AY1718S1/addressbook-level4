package seedu.address.model.task;
//@@author Esilocke
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the deadline of a task in the address book.
 */
public class Deadline {
    public static final String MESSAGE_INVALID_DATE =
            "The specified date is invalid.";
    public static final String DEADLINE_PLACEHOLDER_VALUE = "";

    public final Date date;
    public final String value;

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        if (deadline == null || deadline.equals(DEADLINE_PLACEHOLDER_VALUE)) {
            this.value = DEADLINE_PLACEHOLDER_VALUE;
            this.date = null;
            return;
        }
        this.date = setDateFromArgs(deadline);
        this.value = date.toString();
    }

    /**
     * Returns true if the given string is a valid date.
     * Guarantees: given string format is valid
     */
    public static boolean isValidDeadline(String test) {
        if (test.equals(DEADLINE_PLACEHOLDER_VALUE)) {
            return true;
        }
        try {
            setDateFromArgs(test);
            return true;
        } catch (IllegalValueException e) {
            return false;
        }
    }

    /**
     * Returns a Date object that represents the given date string.
     */
    private static Date setDateFromArgs(String date) throws IllegalValueException {
        Parser deadlineParser = new Parser();
        List<DateGroup> groups = deadlineParser.parse(date);
        List<Date> dates = null;
        for (DateGroup group : groups) {
            dates = group.getDates();
        }
        if (dates == null) {
            throw new IllegalValueException(MESSAGE_INVALID_DATE);
        } else {
            return dates.get(0);
        }
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
