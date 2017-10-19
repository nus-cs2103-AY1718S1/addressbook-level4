package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Corresponding alias list of each country code and its corresponding .countryName().
 * Guarantees: immutable; is valid as declared in {@link #isValidCode(String)}
 */
public class Country {


    public static final String MESSAGE_COUNTRY_CONSTRAINTS =
            "Country codes can only contain numbers, and should be 1-4 digits long";
    public static final String CODE_VALIDATION_REGEX = "\\d{1,4}";
    public final String value;

    /**
     * Validates given country code.
     *
     * @throws IllegalValueException if given code string is invalid.
     */
    public Country(String countryCode) throws IllegalValueException {
        requireNonNull(countryCode);
        String trimmedCountryCode = countryCode.trim();
        if (!isValidCode(trimmedCountryCode)) {
            throw new IllegalValueException(MESSAGE_COUNTRY_CONSTRAINTS);
        }
        this.value = trimmedCountryCode;
    }

    /**
     * Returns true if a given string is a valid person country code.
     */
    public static boolean isValidCode(String test) {
        return test.matches(CODE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Country // instanceof handles nulls
                && this.value.equals(((Country) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
