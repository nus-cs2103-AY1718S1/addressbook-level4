package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthday can only contain numbers and slashes, and it can be blank";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        if (!isValidBirthday(birthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = birthday;
    }

    public Birthday() {
        this.value = "01/01/1900";
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {

        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int currentYear = now.get(Calendar.YEAR);       // The current year

        String[] date = test.split("/");
        if (date.length != 3) {
            return false;
        }

        for (int i = 0; i < date.length; i++) {
            for (int j = 0; j < date[i].length(); j++) {
                if (date[i].charAt(j) > 57 || date[i].charAt(j) < 48) {
                    return false;
                }
            }
        }

        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);

        if (day > 0 && day < 32 && month > 0 && month < 13 && year >= 1900 && year <= currentYear) {
            return true;
        }

        return false;
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
