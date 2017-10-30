package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a birthday field in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidBirthdayNumber(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Birthdays should be in numeric, 6 numbers long "
            + "in the format DDMMYY.";
    public static final String BIRTHDAY_VALIDATION_REGEX = "[0-9]{4,8}";

    public static final String DEFAULT_BIRTHDAY = "No Birthday Added";

    private final String birthdayNumber;

    public Birthday () {
        this.birthdayNumber = DEFAULT_BIRTHDAY; //default value
    }

    /**
     * Validates given birthday input.
     *
     * @throws IllegalValueException if the given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthdayNumber(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.birthdayNumber = trimmedBirthday;
    }

    /**
     * Returns the string value of the birthday
     */
    public String getBirthdayNumber() {
        return birthdayNumber;
    }

    /**
     * Returns true if a given string is a valid birthday number.
     */
    public static boolean isValidBirthdayNumber(String test) {

        if (test.matches(BIRTHDAY_VALIDATION_REGEX) || test.equalsIgnoreCase(DEFAULT_BIRTHDAY)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.birthdayNumber.equals(((Birthday) other).birthdayNumber)); // state check
    }

    @Override
    public int hashCode() {
        return birthdayNumber.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return birthdayNumber;
    }
}
