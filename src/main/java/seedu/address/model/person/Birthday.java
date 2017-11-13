//@@author sunarjo-denny
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {
    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthday should be in the format YYYY/MM/DD";
    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d\\d\\d\\d/\\d\\d/\\d\\d";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        if (birthday.isEmpty()) {
            this.value = "";
            return;
        }
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
    }

    /**
     * Creates Birthday that is empty.
     */
    private Birthday() {
        this.value = "";
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    //@@author HanYaodong
    /**
     * Returns an empty Birthday object.
     */
    public static Birthday getEmptyBirthday() {
        return new Birthday();
    }
    //@@author sunarjo-denny

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
