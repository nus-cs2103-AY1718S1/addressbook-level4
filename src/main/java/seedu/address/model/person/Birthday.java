package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author renkai91
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Person's birthday should be in format: DD/MM/YYYY";
    public static final String MESSAGE_BIRTHDAY2_CONSTRAINTS = "day or month more the 31 or 12";
    public static final String MESSAGE_BIRTHDAY3_CONSTRAINTS = "1,3,5,7,8,10,12 only have 31days";
    public static final String MESSAGE_BIRTHDAY4_CONSTRAINTS = "Feb don't have 30 or 31 days";
    public static final String MESSAGE_BIRTHDAY5_CONSTRAINTS = "Not leap year, got Feb no 29,31 or 31 days";

    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d\\d/\\d\\d/\\d\\d\\d\\d";
    public final String value;
    /*** * Validates given birthday.
    * *
    *
    * @throws IllegalValueException if given birthday address string is invalid.
    */

    public Birthday(String birthday) throws IllegalValueException {
        String trimmedBirthday = (birthday != null) ? birthday : "01/01/2001";
        if (birthday != null && !isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        if (isValidBirthday(trimmedBirthday)) {
            String yes2 = trimmedBirthday.replaceAll("[/]", "");
            int result = Integer.parseInt(yes2);
            int year = result % 10000;
            int month = ((result % 1000000) - year) / 10000;
            int day = result / 1000000;
            if (month > 13 || day > 32) {
                throw new IllegalValueException(MESSAGE_BIRTHDAY2_CONSTRAINTS);
            }
            if ((day == 31) && ((month == 4) || (month == 6) || (month == 9) || (month == 11))) {
                throw new IllegalValueException(MESSAGE_BIRTHDAY3_CONSTRAINTS);
            } else if (month == 2) {
                //leap year
                if (year % 4 == 0) {
                    if ((day == 30) || (day == 31)) {
                        throw new IllegalValueException(MESSAGE_BIRTHDAY4_CONSTRAINTS);
                    }
                } else {
                    if ((day == 29) || (day == 30) || (day == 31)) {
                        throw new IllegalValueException(MESSAGE_BIRTHDAY5_CONSTRAINTS);
                    }
                }
            }
        }
        this.value = trimmedBirthday;
    }
    /**
     * * Returns if a given string is a valid person birthday.
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
        // short circuit if same object
        // instanceof handles nulls
        // state check
        return other == this || (other instanceof Birthday && this.value.equals(((Birthday) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
//@@author
