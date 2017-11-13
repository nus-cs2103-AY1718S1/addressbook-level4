package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author hengyu95
/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthdate(String)}
 */
public class Birthdate {


    public static final String MESSAGE_BIRTHDATE_CONSTRAINTS =
            "A valid date entry is in the form of dd/mm/yyyy";

    public static final String BIRTHDATE_VALIDATION_REGEX = "(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((1[6-9]|"
            + "[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((1[6-9]|[2-9]\\d)\\d{2}))"
            + "|((0[1-9]|1\\d|2[0-8])\\/02\\/((1[6-9]|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)"
            + "(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))";

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
