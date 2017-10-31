package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's postal code in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {


    public static final String MESSAGE_POSTALCODE_CONSTRAINTS =
            "Postal codes can only contain numbers, should be exactly 6 digits long and less than 800000";
    public static final String POSTALCODE_VALIDATION_REGEX = "(?<!\\d)\\d{6}(?!\\d)";
    public static final int POSTALCODE_UPPER_RANGE = 800000;
    public final Boolean isPresent;
    public final String value;

    /**
     * Validates given postal code.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public PostalCode(String postalCode) throws IllegalValueException {
        requireNonNull(postalCode);

        if ("".equals(postalCode)) {
            isPresent = false;
            this.value = postalCode;
        } else {
            String trimmedPostalCode = postalCode.trim();
            if (!isValidPostalCode(trimmedPostalCode)) {
                throw new IllegalValueException(MESSAGE_POSTALCODE_CONSTRAINTS);
            }

            isPresent = true;
            this.value = trimmedPostalCode;
        }
    }

    /**
     * Returns true if a given string is a valid person postal code.
     */
    public static boolean isValidPostalCode(String test) {
        return test.matches(POSTALCODE_VALIDATION_REGEX) && Integer.parseInt(test) <= POSTALCODE_UPPER_RANGE;
    }

    /**
     * Returns true if a valide postal code is present
     * @return
     */
    public boolean isPresentPostalCode() {
        return isPresent;
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
