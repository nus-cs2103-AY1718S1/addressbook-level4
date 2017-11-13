package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author Gideonfu
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemark(String)}
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remark can take any values";

    /*
     * The first character of the remark must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String REMARK_VALIDATION_REGEX = "[^\\s].*";
    public static final String REMARK_NOT_ASSIGNED = "~";

    public final String value;
    /**
     * Validates given remark.
     *
     * @throws IllegalValueException if given remark string is invalid.
     */
    public Remark(String remark) throws IllegalValueException {

        if (remark == null) {
            this.value = REMARK_NOT_ASSIGNED;
        } else {
            if (!isValidRemark(remark)) {
                throw new IllegalValueException(MESSAGE_REMARK_CONSTRAINTS);
            }
            this.value = remark;
        }
    }
    /**
     * Returns true if a given string is a valid person remark.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(REMARK_VALIDATION_REGEX) || test.matches(REMARK_NOT_ASSIGNED);
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
