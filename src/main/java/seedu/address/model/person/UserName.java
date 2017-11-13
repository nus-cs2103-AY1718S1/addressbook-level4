//@@author danielbrzn
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's social media username in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUserName(String)}
 */
public class UserName {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Social media username should only contain alphanumeric characters, underscores or a period character.";

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * It should only contain alphanumeric characters, underscores or a period character
     */
    public static final String USERNAME_VALIDATION_REGEX = "[\\w\\.]+";

    public final String value;

    /**
     * Validates given username.
     *
     * @throws IllegalValueException if given username string is invalid.
     */
    public UserName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedUserName = name.trim();
        if (trimmedUserName.length() != 0 && !isValidUserName(trimmedUserName)) {
            throw new IllegalValueException(MESSAGE_USERNAME_CONSTRAINTS);
        }
        this.value = trimmedUserName;
    }

    /**
     * Returns true if a given string is a valid social media username.
     */
    public static boolean isValidUserName(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserName // instanceof handles nulls
                && this.value.equals(((UserName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
