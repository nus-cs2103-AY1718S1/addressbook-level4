package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

//@@author raisa2010
/**
 * Represents the deadline of a task in the task manager.
 * Guarantees: immutable.
 */
public class Deadline {

    public final String date;

    /**
     * Creates a new deadline.
     */
    public Deadline(String date) {
        requireNonNull(date);
        String trimmedDate = date.trim();
        this.date = trimmedDate;
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
