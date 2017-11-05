//@@author cqhchan
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 *
 */
public class Password {

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
        "Password can take any values, and it should not be blank";

    /*
     * The first character of the password must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_PASSWORD_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given password.
     *
     * @throws IllegalValueException if given password string is invalid.
     */
    public Password(String password) throws IllegalValueException {
        requireNonNull(password);
        if (!isValidPassword(password)) {
            throw new IllegalValueException(MESSAGE_PASSWORD_CONSTRAINTS);
        }
        this.value = password;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(ADDRESS_PASSWORD_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && this.value.equals(((Password) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
