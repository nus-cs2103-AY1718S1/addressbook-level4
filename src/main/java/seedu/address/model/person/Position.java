//@@author sebtsh
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's position in the address book.
 */

public class Position {
    public static final String MESSAGE_POSITION_CONSTRAINTS =
            "Person position can take any values, and it should not be blank";

    /*
     * The first character of the position must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String POSITION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given position.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Position(String position) throws IllegalValueException {
        requireNonNull(position);
        if (!isValidPosition(position)) {
            throw new IllegalValueException(MESSAGE_POSITION_CONSTRAINTS);
        }
        this.value = position;
    }

    /**
     * Returns true if a given string is a valid position.
     */
    public static boolean isValidPosition(String test) {
        return test.matches(POSITION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Position // instanceof handles nulls
                && this.value.equals(((Position) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
