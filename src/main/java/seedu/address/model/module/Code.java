package seedu.address.model.module;

import seedu.address.commons.exceptions.IllegalValueException;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCode(String)}
 */
public class Code {
    public static final String MESSAGE_CODE_CONSTRAINTS =
            "Person names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CODE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullCodeName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Code(String code) throws IllegalValueException {
        requireNonNull(code);
        String trimmedCode = code.trim();
        if (!isValidCode(trimmedCode)) {
            throw new IllegalValueException(MESSAGE_CODE_CONSTRAINTS);
        }
        this.fullCodeName = trimmedCode;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidCode(String test) {
        return test.matches(CODE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullCodeName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Code // instanceof handles nulls
                && this.fullCodeName.equals(((Code) other).fullCodeName)); // state check
    }

    @Override
    public int hashCode() {
        return fullCodeName.hashCode();
    }

}
