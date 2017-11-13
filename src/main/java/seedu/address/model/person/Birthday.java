//@@author viviantan95
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author viviantan95
/**
 * Represents a Person's Birthday in the address book.
 * Guarantees: immutable, is valid as declared in {@link #isValidBirthday(String)}
 */

public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday must be in the format of DD/MM/YYYY";

    public static final String BIRTHDAY_VALIDATION_REGEX =
            "\\d{2}/\\d{2}/\\d{4}";

    public final String value;
    private String browserValue;

    //Validates given Birthday.
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
        this.browserValue = trimmedBirthday;
    }

    /**
     * Returns true if a given String is a valid Birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    //@@author jshoung
    public String getBrowserValue() {
        return browserValue;
    }
    //@@author viviantan95

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof Birthday
                && this.value.equals(((Birthday) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
