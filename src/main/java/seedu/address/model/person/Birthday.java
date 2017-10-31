//@@author zengfengw
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is always valid
 */

public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person's birthday have to be in the format DD-MM-YYYY";
    public static final String BIRTHDAY_VALIDATION_REGEX =
            "^(0[1-9]|[12][0-9]|3[01])[-](0[1-9]|1[012])[-](19|20)\\d\\d$";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */

    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        if (!isValidBirthday(birthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = birthday;
    }

    /**
     * Returns true if a given string is a valid birthday.
     */
    public static boolean isValidBirthday(String birthday) {
        if (birthday.matches(BIRTHDAY_VALIDATION_REGEX) || birthday.matches("")) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); //state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
