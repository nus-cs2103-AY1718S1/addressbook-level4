package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author jelneo
/**
 * Represents the username of a user account
 */
public class Username {

    public static final int USERNAME_MIN_LENGTH = 6;
    public static final String MESSAGE_USERNAME_CHARACTERS_CONSTRAINTS = "Username can only consist of uppercase "
                                                                            + "letters (A-Z), lowercase letters (a-z),"
                                                                            + " digits (0-9) and underscores (_).";
    public static final String MESSAGE_USERNAME_LENGTH_CONSTRAINTS = "Length of username cannot be less than "
                                                                          + USERNAME_MIN_LENGTH + " characters.";
    public static final String USERNAME_VALIDATION_REGEX = "^[a-zA-Z0-9_]+$";

    private String value;

    /**
     * Validates a given username.
     *
     * @param value the username of the user
     * @throws IllegalValueException if given value string is invalid.
     */
    public Username(String value) throws IllegalValueException {
        requireNonNull(value);
        String trimmedUsername = value.trim();
        if (!isValidUsernameLength(value)) {
            throw new IllegalValueException(MESSAGE_USERNAME_LENGTH_CONSTRAINTS);
        }
        if (!hasValidUsernameCharacters(value)) {
            throw new IllegalValueException(MESSAGE_USERNAME_CHARACTERS_CONSTRAINTS);
        }
        this.value = trimmedUsername;
    }

    /**
     * Returns if a given string has a valid username length.
     */
    public static boolean isValidUsernameLength(String testVal) {
        return testVal.length() >= USERNAME_MIN_LENGTH;
    }

    /**
     * Returns if a given string has valid username characters.
     */
    public static boolean hasValidUsernameCharacters(String testVal) {
        return testVal.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Username // instanceof handles nulls
                && this.value.equals(((Username) other).value)); // state check
    }

}
