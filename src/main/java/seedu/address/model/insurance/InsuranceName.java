package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.EmptyFieldException;

/**
 * Represents an insurance's name in LISA.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class InsuranceName {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Insurance names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public InsuranceName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            throw new EmptyFieldException(PREFIX_NAME);
        }
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid insurance name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InsuranceName // instanceof handles nulls
                && this.fullName.equals(((InsuranceName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
