package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's heading/title in addressbook
 * Guarantees: immutable; is valid as declared in {@link #isValidHeader(String)}
 */
public class Header {

    public static final String MESSAGE_HEADER_CONSTRAINTS =
            "Task headers should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String HEADER_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String header;

    /**
     * Validates given header
     *
     * @throws IllegalValueException if given header is invalid
     */
    public Header(String header) throws IllegalValueException {
        requireNonNull(header);
        String trimmedHeader = header.trim();
        if (!isValidHeader(trimmedHeader)) {
            throw new IllegalValueException(MESSAGE_HEADER_CONSTRAINTS);
        }
        this.header = trimmedHeader;
    }

    /**
     * Returns true if given string is a valid task header
     */
    public static boolean isValidHeader(String test) {
        return test.matches(HEADER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return header;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Header // instanceof handles nulls
                && this.header.equals(((Header) other).header)); // state check
    }

    @Override
    public int hashCode() {
        return header.hashCode();
    }
}
