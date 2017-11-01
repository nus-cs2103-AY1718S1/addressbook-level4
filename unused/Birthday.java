package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author LuLechuan
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable.
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthday should be in dd/mm/yyyy format, and it should not be blank";

    /*
     * The first character of the birthday must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Birthday(String birthdayNum) throws IllegalValueException {
        //requireNonNull(birthdayNum);
        this.value = birthdayNum;
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
