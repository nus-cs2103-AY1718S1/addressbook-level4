package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;
//@@author eeching
/**
 * Represents a Person's gender in the address book.
 * Guarantees: immutable.
 */
public class Gender {

    public static final String MESSAGE_GENDER_CONSTRAINTS =
            "must be either a male or female";


    public final String value;

    /**
     * Validates given gender.
     *
     * @throws IllegalValueException if given address string is invalid.
     */

    public Gender() throws IllegalValueException {
        this.value = "";
    }
    public Gender(String gender) throws IllegalValueException {
        if (!(gender.equals("male") || gender.equals("female") || gender.equals(""))) {
            throw new IllegalValueException(MESSAGE_GENDER_CONSTRAINTS);
        }
        this.value = gender;

    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && this.value.equals(((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}