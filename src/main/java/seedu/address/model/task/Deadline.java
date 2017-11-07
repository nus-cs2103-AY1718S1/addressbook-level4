package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.SUFFIX_NO_RECUR_INTERVAL;

import seedu.address.logic.parser.Suffix;

//@@author raisa2010
/**
 * Represents the deadline of a task in the task manager.
 * Guarantees: immutable.
 */
public class Deadline extends TaskDates {

    public final String date;
    public final Suffix recurInterval;

    /**
     * Creates an empty deadline with no recur interval if the deadline is not specified.
     */
    public Deadline() {
        this.date = "";
        this.recurInterval = SUFFIX_NO_RECUR_INTERVAL;
    }

    /**
     * Creates a deadline using the {@code String date} and {@code Suffix recurInterval} given.
     */
    public Deadline(String date, Suffix recurInterval) {
        requireNonNull(date);
        this.date = date.trim();
        this.recurInterval = recurInterval;
    }

    /**
     * Returns a boolean specifying whether the given deadline date is empty.
     */
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
