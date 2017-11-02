package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author deep4k
/**
 * For the alias representation in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRepresentation(String)}
 */
public class Representation {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Alias representations should not be empty";

    public final String representation;

    /**
     * Validates given representation.
     *
     * @throws IllegalValueException if given representation string is invalid.
     */
    public Representation(String representation) throws IllegalValueException {
        requireNonNull(representation);
        if (!isValidRepresentation(representation)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.representation = representation;
    }

    /**
     * Returns true if a given string is a valid alias representation.
     */
    public static boolean isValidRepresentation(String test) {
        return !test.isEmpty();
    }

    @Override
    public String toString() {
        return representation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Representation // instanceof handles nulls
                && this.representation.equals(((Representation) other).representation)); // state check
    }

    @Override
    public int hashCode() {
        return representation.hashCode();
    }
}

