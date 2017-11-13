package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author hengyu95
/**
 * Represents a Person's Instagram account name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUserId(String)}
 */
public class UserId {

    public static final String MESSAGE_USERID_CONSTRAINTS = "ID should contain only letters and numbers.";

    public static final String USERNAME_VALIDATION_REGEX = "(^[a-zA-Z0-9_]*$)";

    public final String value;

    public UserId(String id) throws IllegalValueException {
        requireNonNull(id);
        String trimmedId = id.trim();

        if (!trimmedId.equals("-")) {
            if (!isValidUserId(trimmedId)) {
                throw new IllegalValueException(MESSAGE_USERID_CONSTRAINTS);
            }
        }

        this.value = trimmedId;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */

    public static boolean isValidUserId(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserId // instanceof handles nulls
                && this.value.equals(((UserId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}






