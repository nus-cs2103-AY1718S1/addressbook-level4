package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the deadline of a task in the application.
 * Guarantees: immutable, is valid as declared in {@link #formatDate(String)}
 */
public class Deadline extends TaskDates {

    public final String date;

    /**
     * Validates given deadline date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Deadline(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!trimmedDate.isEmpty() && !TaskDates.isDateValid(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.date = trimmedDate;
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
