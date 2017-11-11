package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author renkai91
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Person's birthday should be in format: DD/MM/YYYY";
    public static final String MESSAGE_BIRTHDAY2_CONSTRAINTS = "Year must be greater than 0000";
    public static final String MESSAGE_BIRTHDAY3_CONSTRAINTS = "Month must be between 01 to 12";
    public static final String MESSAGE_BIRTHDAY4_CONSTRAINTS = "Day must be between 01 to 31";
    public static final String MESSAGE_BIRTHDAY5_CONSTRAINTS = "Only month 01, 03, 05, 07, 08, 10, 12 have 31 days";
    public static final String MESSAGE_BIRTHDAY6_CONSTRAINTS = "Feb do not have 30 or 31 days";
    public static final String MESSAGE_BIRTHDAY7_CONSTRAINTS = "Not leap year, got Feb no 29,31 or 31 days";

    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d\\d/\\d\\d/\\d\\d\\d\\d";
    public static final String NO_BIRTHDAY_DEFAULT = "00000000";
    public final String value;
    /*** * Validates given birthday.
    * *
    *
    * @throws IllegalValueException if given birthday address string is invalid.
    */

    public Birthday(String birthday) throws IllegalValueException {
        String trimmedBirthday = (birthday != null) ? birthday : "01/01/1991";

        if (isValidBirthday(trimmedBirthday) && !birthday.equals("No birthday")) {
            String yes2 = trimmedBirthday.replaceAll("[/]", "");

            isValidBirthdayValue(yes2);

            if (yes2.equals(NO_BIRTHDAY_DEFAULT)) {
                this.value = "No birthday";
            } else {
                this.value = trimmedBirthday;
            }
        } else if (birthday.equals("No birthday")) {
            this.value = "No birthday";
        } else {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
    }

    /**
     * Returns if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    /**
     * Check validity of input values
     */

    public static void isValidBirthdayValue (String birthdayString) throws IllegalValueException {

        if (birthdayString.equals(NO_BIRTHDAY_DEFAULT)) {
            return;
        }

        int result = Integer.parseInt(birthdayString);
        int year = result % 10000;
        int month = ((result % 1000000) - year) / 10000;
        int day = result / 1000000;

        if (year < 1) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY2_CONSTRAINTS);
        } else if (month > 12 || month < 1) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY3_CONSTRAINTS);
        } else if (day > 31 || day < 1) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY4_CONSTRAINTS);
        }


        if ((day == 31) && ((month == 4) || (month == 6) || (month == 9) || (month == 11))) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY5_CONSTRAINTS);
        } else if (month == 2) {
            //leap year
            if ((year % 4) == 0) {
                if ((day == 30) || (day == 31)) {
                    throw new IllegalValueException(MESSAGE_BIRTHDAY6_CONSTRAINTS);
                }
            } else {
                if ((day == 29) || (day == 30) || (day == 31)) {
                    throw new IllegalValueException(MESSAGE_BIRTHDAY7_CONSTRAINTS);
                }
            }
        }
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
