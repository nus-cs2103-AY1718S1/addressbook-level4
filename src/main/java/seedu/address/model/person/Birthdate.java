package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthdate in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthdate(String)}
 */
public class Birthdate {

    public static final String MESSAGE_BIRTHDATE_CONSTRAINTS =
            "Person birthdays should be 8 numbers separated by 2 '/' in the following format - 'DD/MM/YYYY'";
    public static final String BIRTHDATE_VALIDATION_REGEX = "^(0[1-9]|[12][0-9]|3[01])[// /.](0[1-9]|1[012])[// /.](19|20)\\d\\d$";

    public final String value;

    /**
     * Validates given birthdate.
     *
     * @throws IllegalValueException if given birthdate string is invalid.
     */
    public Birthdate(String birthdate) throws IllegalValueException {
        requireNonNull(birthdate);
        String trimmedBirthdate = birthdate.trim();
        if (!isValidBirthdate(trimmedBirthdate)) {
            throw new IllegalValueException(MESSAGE_BIRTHDATE_CONSTRAINTS);
        }
        this.value = trimmedBirthdate;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidBirthdate(String test) {
        return test.matches(BIRTHDATE_VALIDATION_REGEX);
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
