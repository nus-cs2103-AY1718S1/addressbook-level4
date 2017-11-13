//@@author sebtsh
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's status in the address book.
 */

public class Status {
    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "Person status can take any values, and it should not be blank";

    /*
     * The first character of the status must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String STATUS_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given status.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Status(String status) throws IllegalValueException {
        requireNonNull(status);
        if (!isValidStatus(status)) {
            throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
        }
        this.value = status;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches(STATUS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.value.equals(((Status) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
