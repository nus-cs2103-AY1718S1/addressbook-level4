package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Schedule's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ScheduleName {

    public static final String MESSAGE_SCHEDULE_CONSTRAINTS = "Schedule names should contain only "
            + "alphanumeric characters, spaces, underscores and dashes";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String SCHEDULE_VALIDATION_REGEX = "^[a-zA-Z0-9]([\\w -]*[a-zA-Z0-9])?$";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public ScheduleName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_SCHEDULE_CONSTRAINTS);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid schedule name.
     */
    public static boolean isValidName(String test) {
        return test.matches(SCHEDULE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleName // instanceof handles nulls
                && this.fullName.equals(((ScheduleName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
