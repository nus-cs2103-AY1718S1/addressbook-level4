//@@author conantteo
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday format should be 'DD/MM/YYYY', and it should not be blank\n"
                    + "Please check if the birthday is valid and is a valid leap day";
    // This regex guarantees that a Birthday format is DD/MM/YYYY and is not leap day.
    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?:(?:31(/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
            + "(/)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(/)0?2\\3(?:(?:"
            + "(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?"
            + "[1-9]|1\\d|2[0-8])(/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    // This regex guarantees that a Birthday month is MM and the range of values is from [01-12].
    public static final String BIRTHDAY_MONTH_REGEX = "([0][1-9])|([1][0-2])";

    public final String value;
    private String birthdayMonth;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (birthday.length() != 0 && !isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        } else if (isValidBirthday(trimmedBirthday)) {
            this.birthdayMonth = trimmedBirthday.split("/")[1];
        }
        this.value = trimmedBirthday;
    }


    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given String matches exactly 2 digits from 01 to 12 which is a valid birthday month.
     */
    public static boolean isValidMonth(String test) {
        return test.matches(BIRTHDAY_MONTH_REGEX);
    }

    public String getBirthdayMonth() {
        return birthdayMonth;
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
