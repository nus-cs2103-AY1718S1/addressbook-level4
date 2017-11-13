package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's avatar filepath in the address book.
 */
public class Avatar {

    public static final String MESSAGE_AVATAR_CONSTRAINTS =
            "Person avatar can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String AVATAR_VALIDATION_REGEX = "[^\\s].*";
    public static final String AVATAR_PLACEHOLDER_VALUE = "";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Avatar(String address) throws IllegalValueException {
        if (address == null) {
            this.value = AVATAR_PLACEHOLDER_VALUE;
            return;
        }
        if (!isValidAvatar(address)) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
        this.value = address;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidAvatar(String test) {
        return test.matches(AVATAR_VALIDATION_REGEX) || test.equals(AVATAR_PLACEHOLDER_VALUE);
    }

    //@@author jeffreygohkw
    @Override
    public String toString() {
        return value;
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.value.equals(((Avatar) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
