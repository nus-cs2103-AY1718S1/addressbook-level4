package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {


    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday has to be in the format dd-mm-yyyy";
    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}";
    public static final String BIRTHDAY_EMPTY = "-";
    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        if (birthday == null) {
            this.value = BIRTHDAY_EMPTY;
        } else {
            String trimmedBirthday = birthday.trim();
            if (!isValidBirthday(trimmedBirthday)) {
                throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
            }
            this.value = trimmedBirthday;
        }
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX) || test.matches(BIRTHDAY_EMPTY);
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
