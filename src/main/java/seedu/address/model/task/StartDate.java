package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.SUFFIX_NO_RECUR_INTERVAL;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.Suffix;

/**
 * Represents the Starting Date of a given task in the application.
 * Guarantees: immutable; is valid as declared in
 */
public class StartDate extends TaskDates {

    public final String date;
    public final Suffix recurInterval;

    public StartDate() {
        this.date = "";
        this.recurInterval = SUFFIX_NO_RECUR_INTERVAL;
    }

    /**
     * Validates given starting date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public StartDate(String date, Suffix recurInterval) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!trimmedDate.isEmpty() && !TaskDates.isDateValid(trimmedDate)) {
            throw new IllegalValueException(TaskDates.MESSAGE_DATE_CONSTRAINTS);
        }
        this.date = date.trim();
        this.recurInterval = recurInterval;
    }

    public boolean isEmpty() {
        return date.isEmpty();
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.date.equals(((StartDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
