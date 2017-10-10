package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 *
 */
public class PostalCode {

    public static final String MESSAGE_POSTAL_CODE_CONSTRAINTS =
            "Person postal code should contain only a string of 6 digits";

    /*
     * Person names should only contain alphanumeric characters and spaces, and it should not be blank
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String POSTAL_CODE_VALIDATION_REGEX = "^[0-9]{6}$";

    public final String postalCode;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public PostalCode(String postalCode) throws IllegalValueException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (!isValidPostalCode(trimmedPostalCode)) {
            throw new IllegalValueException(MESSAGE_POSTAL_CODE_CONSTRAINTS);
        }
        this.postalCode = trimmedPostalCode;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidPostalCode(String test) {
        return test.matches(POSTAL_CODE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return postalCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostalCode // instanceof handles nulls
                && this.postalCode.equals(((PostalCode) other).postalCode)); // state check
    }

    @Override
    public int hashCode() {
        return postalCode.hashCode();
    }

}
