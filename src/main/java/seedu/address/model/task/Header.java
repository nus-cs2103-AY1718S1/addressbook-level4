//@@author srishag
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's header in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHeader(String)}
 */
public class Header {

    public static final String MESSAGE_HEADER_CONSTRAINTS =
            "Task's headers can take any values, and it should not be blank";

    /*
     * The first character of the header must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String HEADER_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given header.
     *
     * @throws IllegalValueException if given header string is invalid.
     */
    public Header(String header) throws IllegalValueException {
        requireNonNull(header);
        if (!isValidHeader(header)) {
            throw new IllegalValueException(MESSAGE_HEADER_CONSTRAINTS);
        }
        this.value = header;
    }

    /**
     * Returns true if a given string is a valid task header.
     */
    public static boolean isValidHeader(String test) {
        return test.matches(HEADER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Header // instanceof handles nulls
                && this.value.equals(((Header) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

