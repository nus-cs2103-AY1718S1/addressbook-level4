package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a birthday field in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidBirthdayFormat(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Birthdays should be numeric with 4 or 6 or 8 numbers "
            + "long in the format DDMM or DDMMYY or DDMMYYYY.";
    public static final String MESSAGE_INVALID_DAY_ENTERED = "The day should be within the range 1 to 31";
    public static final String MESSAGE_INVALID_MONTH_ENTERED = "The month should be within the range 1 to 12";

    public static final String BIRTHDAY_VALIDATION_REGEX = "^(\\d{4}|\\d{6}|\\d{8})";

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
        if (!isValidBirthdayFormat(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        } else if (!isValidDayEntered(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_INVALID_DAY_ENTERED);
        } else if (!isValidMonthEntered(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_INVALID_MONTH_ENTERED);
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
     * Returns true if a given string has a valid birthday day number.
     */
    public static boolean isValidDayEntered(String test) {

        if ((test.substring(0, 2).compareTo("32") < 0 && test.substring(0, 2).compareTo("00") > 0)
                || test.equalsIgnoreCase(DEFAULT_BIRTHDAY)) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if a given string has a valid birthday month number.
     */
    public static boolean isValidMonthEntered(String test) {

        if ((test.substring(2, 4).compareTo("13") < 0 && test.substring(2, 4).compareTo("00") > 0)
                || test.equalsIgnoreCase(DEFAULT_BIRTHDAY)) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if a given string is a valid birthday number.
     */
    public static boolean isValidBirthdayFormat(String test) {

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
