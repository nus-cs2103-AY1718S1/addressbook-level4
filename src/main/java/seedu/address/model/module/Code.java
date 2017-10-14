package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Module code in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidCode(String)}
 */
public class Code {
    public static final String MESSAGE_CODE_CONSTRAINTS =
            "Module code should start with letters and followed by alphanumeric characters, and it should not be blank";

    /*
     * The valid code should be 2 letters, 4 digits and followed by 1 or 0 letter.
     */
    public static final String CODE_VALIDATION_REGEX = "[a-zA-Z]{2}\\\\d{4}[a-zA-Z]?";

    public final String fullCodeName;

    /**
     * Validates given module code.
     *
     * @throws IllegalValueException if given code string is invalid.
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
     * Returns true if a given string is a valid module code.
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
