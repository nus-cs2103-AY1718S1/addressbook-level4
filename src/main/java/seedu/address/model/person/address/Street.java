package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the street in Person's address.
 * Guarantees: immutable; is valid as declared in {@link #isValidStreet(String)}
 */
public class Street {
    public static final String MESSAGE_STREET_CONSTRAINTS =
            "Street can contain alphanumeric characters and spaces, and it should not be blank";
    private static final String STREET_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    public Street(String street) throws IllegalValueException {
        requireNonNull(street);
        String trimmedStreet = street.trim();

        if (!isValidStreet(trimmedStreet)) {
            throw new IllegalValueException(MESSAGE_STREET_CONSTRAINTS);
        }
        this.value = trimmedStreet;
    }

    /**
     * Returns true if a given string is a valid street in an address.
     */
    public static boolean isValidStreet (String test) {
        return test.matches(STREET_VALIDATION_REGEX);
    }
}
