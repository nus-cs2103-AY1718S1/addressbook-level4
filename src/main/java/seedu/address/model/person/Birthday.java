package seedu.address.model.person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;



/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {
    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
        "Person Birthday should be in the format of DD/MM/YYYY";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        String trimmedBirthday = birthday == null ? null : birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    static boolean isValidBirthday(String test) {
        if (test == null) {
            return true;
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try {
            Date strToDate = format.parse(test);      //tries to parse provided string in given format
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Birthday // instanceof handles nulls
            && (this.value == ((Birthday) other).value || this.value.equals(((Birthday) other).value))); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
