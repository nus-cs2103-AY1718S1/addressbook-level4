package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Group in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGroupName(String)}
 */
public class Group {

    public static final String MESSAGE_GROUP_CONSTRAINTS =
            "Person names should only contain alphanumeric characters and spaces, and it should not be blank";
    /*
    * The first character of the group must not be a whitespace,
    * otherwise " " (a blank string) becomes a valid input.
    */
    public static final String GROUP_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private String name;

    /**
     * Validates given group name.
     *
     * @throws IllegalValueException if the given group name string is invalid.
     */
    public Group(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidGroupName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_GROUP_CONSTRAINTS);
        }
        this.name = trimmedName;
    }

    /**
     * Returns true if a given string is a valid group name.
     */
    public static boolean isValidGroupName(String test) {
        return test.matches(GROUP_VALIDATION_REGEX);
    }

    public void setName(String name) { requireNonNull(name); this.name = name; }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group // instanceof handles nulls
                && this.name.equals(((Group) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return name;
    }

}
