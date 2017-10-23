package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.util.regex.Matcher;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the Date of a single task or event in the application.
 * Guarantees: immutable; is valid as declared in {@link TaskDates#isDateValid(String)}
 */
public class SingleEventDate extends TaskDates {

    public final String date;

    /**
     * Validates given starting date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public SingleEventDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        this.date = trimmedDate;
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
                || (other instanceof SingleEventDate // instanceof handles nulls
                && this.date.equals(((SingleEventDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
