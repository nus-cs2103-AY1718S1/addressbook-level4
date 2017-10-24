package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the number of accesses to a person's data.
 */
public class AccessCount {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Number of access cannot be less than 0.";

    private int value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public AccessCount(int accessCount) {
        this.value = accessCount;
    }

    @Override
    public String toString() {
        return "Accesses: " + Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccessCount // instanceof handles nulls
                && this.value == ((AccessCount) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value;
    }

    public int numAccess() {
        return value;
    }

}
