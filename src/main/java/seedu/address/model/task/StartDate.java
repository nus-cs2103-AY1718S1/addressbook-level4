package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the Starting Date of a given task in the application.
 * Guarantees: immutable; is valid as determined when parsing occurs in 
 * {@link ParserUtil.parseDate(String)}
 */
public class StartDate extends TaskDates {

    public final String date;

    /**
     * Validates given starting date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public StartDate(String date) {
        requireNonNull(date);
        this.date = date.trim();
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
