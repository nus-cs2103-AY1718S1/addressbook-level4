package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthday can only contain numbers and slashes, and it can be blank";

    public static final String BIRTHDAY_VALIDATION_REGEX = "[^\\s].*";

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
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
