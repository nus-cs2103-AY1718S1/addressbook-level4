package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author chairz
/**
 * Represents the priority level of a task in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority can only contain number 0, 1 or 2";

    public final String value;

    /**
     * Validates given priority level.
     *
     * @throws IllegalValueException if given priority int is invalid.
     */
    public Priority(String  priority) throws IllegalValueException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!isValidPriority(trimmedPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = trimmedPriority;
    }

    /**
     * Returns true is the given integer is a valid priority level
     */
    public static boolean isValidPriority(String test) {
        return Integer.parseInt(test) <= 2 && Integer.parseInt(test) >= 0;
    }

    @Override
    public String toString() {
        return value; }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && Integer.parseInt(this.value) == Integer.parseInt(((Priority) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
