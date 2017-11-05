package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author jacoblipech
/**
 * Represents a birthday field in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidBirthdayFormat(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Birthdays should be numeric in the format "
            + "DD/MM/YY or DD/MM/YYYY.";

    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
            + "(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3"
            + "(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$"
            + "|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

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
    public static boolean isValidBirthdayFormat(String test) {

        return test.matches(BIRTHDAY_VALIDATION_REGEX) || test.equalsIgnoreCase(DEFAULT_BIRTHDAY);
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
