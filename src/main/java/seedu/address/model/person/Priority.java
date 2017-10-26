/**
 * @@author Sebastian
 */
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's priority in the address book.
 */

public class Priority {
    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Person priority can take only H, M, or L as inputs.";

    /*
     * Only H, M and L are allowed as inputs.
     */
    public static final String PRIORITY_VALIDATION_REGEX = "[HML]";

    public final String value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        requireNonNull(priority);
        if (!isValidPriority(priority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = priority;
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
