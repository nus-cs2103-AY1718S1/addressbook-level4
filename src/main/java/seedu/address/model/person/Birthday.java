package seedu.address.model.person;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author frozventus
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {


    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday has to be in the format dd-mm-yyyy";
    public static final String BIRTHDAY_VALIDATION_REGEX = "(0[1-9]|[12]\\d|3[01])[-](0[1-9]|1[0-2])[-]\\d{4}";
    public static final String BIRTHDAY_NOT_ASSIGNED = "~";
    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {

        if (birthday == null) {
            this.value = BIRTHDAY_NOT_ASSIGNED;
        } else {
            String trimmedBirthday = birthday.trim();

            if (!trimmedBirthday.equals(BIRTHDAY_NOT_ASSIGNED)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateFormat.setLenient(false);

                try {
                    dateFormat.parse(trimmedBirthday);
                } catch (ParseException e) {
                    throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
                }
            }

            if (!isValidBirthday(trimmedBirthday)) {
                throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
            }
            this.value = trimmedBirthday;
        }
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX) || test.matches(BIRTHDAY_NOT_ASSIGNED);
    }

    public String getMonth() {
        String[] args = value.split("-");

        return args[1];
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
