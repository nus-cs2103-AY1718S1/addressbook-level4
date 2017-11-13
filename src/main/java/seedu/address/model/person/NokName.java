package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's Next-of-Kin's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNokName(String)}
 */
public class NokName {

    public static final String MESSAGE_NOK_NAME_CONSTRAINTS =
            "NOK names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NOK_NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String NOK_NAME_UNDEFINED_DEFAULT = "NIL";

    public final String value;

    /**
     * Validates given NOK name.
     *
     * @throws IllegalValueException if given NOK name string is invalid.
     */
    public NokName(String nokName) throws IllegalValueException {
        if (nokName == null) {
            this.value = NOK_NAME_UNDEFINED_DEFAULT;
        } else {
            if (!isValidNokName(nokName)) {
                throw new IllegalValueException(MESSAGE_NOK_NAME_CONSTRAINTS);
            }
            this.value = nokName;
        }
    }

    /**
     * Returns true if a given string is a valid NOK name.
     */
    public static boolean isValidNokName(String test) {
        return test.matches(NOK_NAME_VALIDATION_REGEX) || test.equals(NOK_NAME_UNDEFINED_DEFAULT);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NokName // instanceof handles nulls
                && this.value.equals(((NokName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
