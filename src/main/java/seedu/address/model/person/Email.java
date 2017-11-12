package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final String MESSAGE_EMAIL_CONSTRAINTS =
        "Person emails should be 2 alphanumeric/period strings separated by '@'";
    public static final String EMAIL_VALIDATION_REGEX = "[\\w\\.]+@[\\w\\.]+";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Email(String email) throws IllegalValueException {
        String trimmedEmail = email == null ? null : email.trim();
        if (!isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.value = trimmedEmail;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidEmail(String test) {
        return test == null || test.matches(EMAIL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    //@@author chrisboo
    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Email)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Email) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Email) other).value)) { // state check
            return true;
        }

        return false;
    }
    //@@author

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
