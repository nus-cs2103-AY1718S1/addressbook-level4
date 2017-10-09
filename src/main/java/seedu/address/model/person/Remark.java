package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; can take in any strings including blanks
 */
public class Remark {

    public final String value;

    /**
     * Validates given remark.
     *
     * @throws IllegalValueException if given remark string is invalid.
     */
    public Remark(String remark) throws IllegalValueException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!isValidRemark(trimmedRemark)) {
            throw new IllegalValueException("Remark should be a String");
        }
        this.value = trimmedRemark;
    }

    /**
     * Returns if a given string is a valid person remark.
     */
    public static boolean isValidRemark(String test) {
        return (test instanceof String);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
