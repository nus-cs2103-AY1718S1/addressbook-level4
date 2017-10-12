package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's postal code in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {
    public static final String MESSAGE_POSTAL_CODE_CONSTRAINTS =
            "Postal code can only be 6 digits";
    public static final String POSTAL_CODE_VALIDATION_REGEX = "\\d{6}";

    public final String value;

    /**
     * Validates given postal code.
     * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
     */
    public PostalCode(String postalCode) throws IllegalValueException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (!isValidPostalCode(trimmedPostalCode)) {
            throw new IllegalValueException(MESSAGE_POSTAL_CODE_CONSTRAINTS);
        }
        this.value = trimmedPostalCode;
    }

    /**
     * Returns true if a given string is a valid person postal code.
     */
    public static boolean isValidPostalCode(String test) {
        return test.matches(POSTAL_CODE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
     public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostalCode // instanceof handles nulls
                && this.value.equals(((PostalCode) other).value)); // state check
    }

    @Override
     public int hashCode() {
        return value.hashCode();
    }
}
