package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's occupation in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOccupation(String)}
 */
public class Occupation {

    public static final String MESSAGE_OCCUPATION_CONSTRAINTS =
            "Person occupation should be 2 alphanumeric strings separated by ','";
    public static final String OCCUPATION_VALIDATION_REGEX = "[^\\s]+,[^\\s]+";

    public final String value;

    /**
     * Validates given occupation.
     *
     * @throws IllegalValueException if given occupation string is invalid.
     */
    public Occupation(String occupation) throws IllegalValueException {
        requireNonNull(occupation);
        String trimmedOccupation = occupation.trim();
        if (!isValidOccupation(trimmedOccupation)) {
            throw new IllegalValueException(MESSAGE_OCCUPATION_CONSTRAINTS);
        }
        this.value = trimmedOccupation;
    }

    /**
     * Returns if a given string is a valid person occupation.
     */
    public static boolean isValidOccupation(String test) {
        return test.matches(OCCUPATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Occupation // instanceof handles nulls
                && this.value.equals(((Occupation) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
