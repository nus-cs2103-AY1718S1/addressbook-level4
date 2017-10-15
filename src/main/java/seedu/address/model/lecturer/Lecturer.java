package seedu.address.model.lecturer;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Lecturer in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidLecturerName(String)}
 */
public class Lecturer {

    public static final String MESSAGE_LECTURER_CONSTRAINTS = "Lecturer names should be alphabetic";
    public static final String LECTURER_VALIDATION_REGEX = "[^\\s].*";

    public final String lecturerName;

    /**
     * Validates given Lecturer name.
     *
     * @throws IllegalValueException if the given lecturer name string is invalid.
     */
    public Lecturer(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidLecturerName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_LECTURER_CONSTRAINTS);
        }
        this.lecturerName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid Lecturer name.
     */
    public static boolean isValidLecturerName(String test) {
        return test.matches(LECTURER_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Lecturer // instanceof handles nulls
                && this.lecturerName.equals(((Lecturer) other).lecturerName)); // state check
    }

    @Override
    public int hashCode() {
        return lecturerName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + lecturerName + ']';
    }

}
