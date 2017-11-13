package seedu.address.model.relationship;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in
 */
public class Relationship {

    public static final String MESSAGE_REL_CONSTRAINTS = "Relationship types should be alphanumeric";
    //public static final String REL_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String relType;

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Relationship(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        this.relType = trimmedName;
    }
    //@@author huiyiiih
    /**
     * Concatenates the name of the given person and the relationship to form a string
     * @param name          Name of the person
     * @param relation      Relationship of the person
     */
    public Relationship(String name, Set<Relationship> relation) {
        this.relType = name + " " +  relation;
    }
    //@@author

    /*/**
     * Returns true if a given string is a valid relationship name.
     */
    /*public static boolean isValidRelType(String test) {
        return test.matches(REL_VALIDATION_REGEX);
    }*/

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Relationship // instanceof handles nulls
            && this.relType.equals(((Relationship) other).relType)); // state check
    }

    @Override
    public int hashCode() {
        return relType.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return relType;
    }

}
