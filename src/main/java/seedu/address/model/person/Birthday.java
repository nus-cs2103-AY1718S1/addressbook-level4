package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author nadhira15
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */

public class Birthday {

    public static final String DEFAULT_VALUE = "-";

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday should contain 8 digit number, first 2 digit for day (01-30), second 2 digit for month (01-12),"
                    + " and last 4 digit for year, separated by slash (/)";
    public static final String BIRTHDAY_VALIDATION_REGEX = "[0-3][\\d][/][0-1][\\d][/]\\d{4}";
    public final String value;

    /**
     * Validates given birthday number.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX) || test.equals(DEFAULT_VALUE);
    }

    /**
     * Returns true if value is not the default value (No Birthday)
     * @return
     */
    public boolean isNotDefault() {
        return !DEFAULT_VALUE.equals(value);
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
