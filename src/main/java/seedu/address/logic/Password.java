package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author jelneo
/**
 * Represents the password of a user account.
 */
public class Password {
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final String MESSAGE_PASSWORD_CHARACTERS_CONSTRAINTS = "Password can only consist of uppercase "
            + "letters (A-Z), lowercase letters (a-z),"
            + " digits (0-9) and special characters (!@#$&()_-.+)";
    public static final String MESSAGE_PASSWORD_LENGTH_CONSTRAINTS = "Length of password cannot be less than "
            + PASSWORD_MIN_LENGTH + " characters";
    public static final String PASSWORD_VALIDATION_REGEX = "^[a-zA-Z0-9!@#$&()_\\-.+]+$";

    private String value;

    /**
     * Validates a given password.
     * @param value the password of the user
     * @throws IllegalValueException if given value string is invalid
     */
    public Password(String value) throws IllegalValueException {
        requireNonNull(value);
        String trimmedPassword = value.trim();
        if (!isValidPasswordLength(value)) {
            throw new IllegalValueException(MESSAGE_PASSWORD_LENGTH_CONSTRAINTS);
        }
        if (!hasValidPasswordCharacters(value)) {
            throw new IllegalValueException(MESSAGE_PASSWORD_CHARACTERS_CONSTRAINTS);
        }
        this.value = trimmedPassword;
    }

    /**
     * Checks if a given string has a valid password length.
     * @return {@code true} if the given string is a of a valid password length,
     * otherwise {@code false}
     */
    public static boolean isValidPasswordLength(String testVal) {
        return testVal.length() >= PASSWORD_MIN_LENGTH;
    }

    /**
     * Checks if a given string has valid password characters.
     * @return {@code true} if the given string contains valid password characters,
     * otherwise {@code false}
     */
    public static boolean hasValidPasswordCharacters(String testVal) {
        return testVal.matches(PASSWORD_VALIDATION_REGEX);
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

}
