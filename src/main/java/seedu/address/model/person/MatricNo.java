package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author April0616
/**
 * Represents a Person's Matriculation Number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatricNo(String)}
 */
public class MatricNo {

    public static final String MESSAGE_MATRIC_NO_CONSTRAINTS =
            "Person's matriculation number should be a 9-character string starting with 'A' or 'a', "
                    + "followed by 7 digits, and ending with a letter.";
    public static final String MATRIC_NO_VALIDATION_REGEX = "([Aa])(\\d{7})([a-zA-Z])";

    public final String value;

    /**
     * Initializes matricNo objeccts and validates given matricNo.
     * @throws IllegalValueException if the given matricNo string is invalid.
     */
    public MatricNo(String matricNo) throws IllegalValueException {
        requireNonNull(matricNo);
        String trimmedMatricNo = matricNo.trim();
        if (!isValidMatricNo(trimmedMatricNo)) {
            throw new IllegalValueException(MESSAGE_MATRIC_NO_CONSTRAINTS);
        }
        this.value = trimmedMatricNo;
    }

    /**
     * Returns if a given string is a valid person matricNo.
     */
    public static boolean isValidMatricNo(String test) {
        return test.matches(MATRIC_NO_VALIDATION_REGEX) || test.equals("");
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if two matric numbers are the same.
     * 'A0162533K' equals 'a0162533k'
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatricNo // instanceof handles nulls
                && this.value.toUpperCase().equals(((MatricNo) other).value.toUpperCase())); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
