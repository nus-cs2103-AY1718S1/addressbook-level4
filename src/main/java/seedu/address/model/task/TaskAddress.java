package seedu.address.model.task;

//@@author jeffreygohkw
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskAddress(String)}
 */
public class TaskAddress {
    public static final String MESSAGE_TASK_ADDRESS_CONSTRAINTS =
            "Task addresses can take any values, and it should not be blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";
    public static final String ADDRESS_PLACEHOLDER_VALUE = "";

    public final String taskAddress;
    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public TaskAddress(String address) throws IllegalValueException {
        if (address == null) {
            this.taskAddress = ADDRESS_PLACEHOLDER_VALUE;
            return;
        }
        if (!isValidTaskAddress(address)) {
            throw new IllegalValueException(MESSAGE_TASK_ADDRESS_CONSTRAINTS);
        }
        this.taskAddress = address;
    }

    /**
     * Returns true if a given string is a valid task address.
     */
    public static boolean isValidTaskAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX) || test.equals(ADDRESS_PLACEHOLDER_VALUE);
    }

    @Override
    public String toString() {
        return taskAddress;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskAddress // instanceof handles nulls
                && this.taskAddress.equals(((TaskAddress) other).taskAddress)); // state check
    }

    @Override
    public int hashCode() {
        return taskAddress.hashCode();
    }
}

