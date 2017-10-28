package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's home number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHomeNumber(String)}
 */
public class HomeNumber {


    public static final String MESSAGE_HOME_NUMBER_CONSTRAINTS =
            "Home numbers can only contain numbers, and should be at least 3 digits long";
    public static final String HOME_NUMBER_VALIDATION_REGEX = "\\d{3,}";
    public static final String HOME_NUMBER_TEMPORARY = "NIL";

    public final String value;

    /**
     * Validates given home number.
     *
     * @throws IllegalValueException if given home string is invalid.
     */
    public HomeNumber(String homeNumber) throws IllegalValueException {
        if(homeNumber == null){
          this.value = HOME_NUMBER_TEMPORARY;
        } else {
            String trimmedHomeNumber = homeNumber.trim();
            if (!isValidHomeNumber(trimmedHomeNumber)) {
                throw new IllegalValueException(MESSAGE_HOME_NUMBER_CONSTRAINTS);
            }
            this.value = trimmedHomeNumber;
        }
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidHomeNumber(String test) {
        return test.matches(HOME_NUMBER_VALIDATION_REGEX)
                || test.matches(HOME_NUMBER_TEMPORARY);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HomeNumber // instanceof handles nulls
                && this.value.equals(((HomeNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
