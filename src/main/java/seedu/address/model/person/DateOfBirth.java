package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's date of birth in the address book.
 */
public class DateOfBirth {

    public static final String MESSAGE_DOB_CONSTRAINTS =
            "Person's date of birth should only contain numeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DOB_VALIDATION_REGEX = "[0-9][0-9]\\s+[0-9][0-9]\\s+[0-9][0-9][0-9][0-9].*";

    public final String finalDateOfBirth;

    /**
     * Validates given Date of Birth.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public DateOfBirth(String dob) throws IllegalValueException {
        requireNonNull(dob);
        String trimmedDateOfBirth = dob.trim();
        if (!isValidDateOfBirth(trimmedDateOfBirth)) {
            throw new IllegalValueException(MESSAGE_DOB_CONSTRAINTS);
        }
        this.finalDateOfBirth = trimmedDateOfBirth;
    }

    /**
     * Returns true if a given string is a valid person date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        return test.matches(DOB_VALIDATION_REGEX);
    }
    @Override
    public String toString() {
        return finalDateOfBirth;
    }

    @Override
    public int hashCode() {
        return finalDateOfBirth.hashCode();
    }
}
