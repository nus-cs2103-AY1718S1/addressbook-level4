package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.SUFFIX_NO_RECUR_INTERVAL;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.Suffix;

/**
 * Represents the deadline of a task in the application.
 * Guarantees: immutable, is valid as declared in
 */
public class Deadline extends TaskDates {

    public final String date;
    public final Suffix recurInterval;

    public Deadline() {
        this.date = "";
        this.recurInterval = SUFFIX_NO_RECUR_INTERVAL;
    }

    /**
     * Validates given deadline date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Deadline(String date, Suffix recurInterval) throws IllegalValueException {
        requireNonNull(date);
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
                || (other instanceof Deadline // instanceof handles nulls
                && this.date.equals(((Deadline) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
