package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author leonchowwenhao
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthdays should be 6-8 numbers separated by '/', '-' or '.' with the format of day/month/year"
            + "\nExamples of valid format: 01/12/1990, 6-3-1991, 18-07-1992";
    public static final String BIRTHDAY_VALIDATION_REGEX = "^(0[1-9]|[12][\\d]|3[01]|[1-9])[///./-]"
            + "(0[1-9]|1[012]|[1-9])[///./-](19|20)\\d\\d$";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        if (birthday.equals("")) {
            this.value = birthday;
        } else {
            String trimmedBirthday = birthday.trim();
            if (!isValidBirthday(trimmedBirthday)) {
                throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
            }
            this.value = trimmedBirthday;
        }
    }

    /**
     * Returns if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return (isValidBirthdayFormat(test) && isValidDate(test));
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        Boolean result = false;
        String[] birthdayParts = splitBirthday(test);
        int day = Integer.parseInt(birthdayParts[0]);
        int month = Integer.parseInt(birthdayParts[1]);
        int year = Integer.parseInt(birthdayParts[2]);

        if (month == 2) {
            if (isLeapYear(year) && day <= 29) {
                result = true;
            } else if (day <= 28) {
                result = true;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day <= 30) {
                result = true;
            }
        } else {
            result = true;
        }

        return (result);
    }

    /**
     * Returns if a given string has a valid person birthday format.
     */
    public static boolean isValidBirthdayFormat(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    /**
     * Returns an array with String split into 3 parts.
     */
    public static String[] splitBirthday(String test) {
        String[] birthdayParts = test.split("[///./-]");

        return birthdayParts;
    }

    /**
     * Returns if a given int is a leap year.
     */
    public static boolean isLeapYear(int year) {
        Boolean result = false;

        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            result = true;
        }

        return result;
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
