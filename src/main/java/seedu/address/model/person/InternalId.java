package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's id in the address book.
 * Guarantees: immutable; is valid long as it is a positive integer
 */
public class InternalId {

    public static final String MESSAGE_ID_CONSTRAINTS = "Id must be a positive interger.";
    public final int value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public InternalId(int id) throws IllegalValueException {
        if (id < 0) {
            throw new IllegalValueException(MESSAGE_ID_CONSTRAINTS);
        }
        this.value = id;
    }

    public int getId() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InternalId // instanceof handles nulls
                && this.value == (((InternalId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

}
