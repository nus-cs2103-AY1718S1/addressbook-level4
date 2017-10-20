package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthdate(String)}
 */
public class Birthdate {


    public static final String MESSAGE_BIRTHDATE_CONSTRAINTS =
            "Birthdate should contain valid date entries separated by . - /";
    public static final String BIRTHDATE_VALIDATION_REGEX = "([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([0]?[1-9]|[1]" +
            "[0-2])[./-]([0-9]{4}|[0-9]{2})";        // dd/mm/yyyy or d/m/yy or d.m.yyyy with separators: . - /

    public static final String BIRTHDATE_VALIDATION_REGEX2 = "([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([1][0-2]|[0]?[1-9])";
                   // dd/mm or d/m or d.m with separators: . - /

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Birthdate(String birthdate) throws IllegalValueException {
        requireNonNull(birthdate);
        String trimmedBirthdate = birthdate.trim();
        if (!trimmedBirthdate.equals("-")) {
            if (!isValidBirthdate(trimmedBirthdate)) {
                throw new IllegalValueException(MESSAGE_BIRTHDATE_CONSTRAINTS);
            }
        }

        this.value = trimmedBirthdate;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidBirthdate(String test) {
        return test.matches(BIRTHDATE_VALIDATION_REGEX) || test.matches(BIRTHDATE_VALIDATION_REGEX2);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthdate // instanceof handles nulls
                && this.value.equals(((Birthdate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
