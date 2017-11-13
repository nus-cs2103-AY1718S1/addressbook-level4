//@@author duyson98

package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a reminder's priority in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority can only be Low / Medium / High";
    public static final String PRIORITY_VALIDATION_REGEX = "(?:Low|Medium|High)";
    public final String value;

    /**
     * Validates given priority word.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!isValidPriority(trimmedPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = trimmedPriority;
    }

    /**
     * Returns true if a given string is a valid reminder priority word.
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
