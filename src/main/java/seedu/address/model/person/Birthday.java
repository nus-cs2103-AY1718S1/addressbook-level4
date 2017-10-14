package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        if (!isValidBirthday(birthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = birthday;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    static boolean isValidBirthday(String birthday) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try {
            Date strToDate=format.parse(birthday);      //tries to parse provided string in given format
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
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

