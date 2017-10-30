//@@author arturs68
package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Group in the address book.
 * Guarantees: immutable; groupName is valid as declared in {@link #isValidGroupName(String)}
 */
public class Group {

    public static final String MESSAGE_GROUP_CONSTRAINTS =
            "Person names should only contain alphanumeric characters and spaces, and it should not be blank";
    /*
    * The first character of the group must not be a whitespace,
    * otherwise " " (a blank string) becomes a valid input.
    */
    public static final String GROUP_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String groupName;

    /**
     * Validates given group groupName.
     *
     * @throws IllegalValueException if the given group groupName string is invalid.
     */
    public Group(String groupName) throws IllegalValueException {
        requireNonNull(groupName);
        String trimmedName = groupName.trim();
        if (!isValidGroupName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_GROUP_CONSTRAINTS);
        }
        this.groupName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid group groupName.
     */
    public static boolean isValidGroupName(String test) {
        return test.matches(GROUP_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group // instanceof handles nulls
                && this.groupName.equals(((Group) other).groupName)); // state check
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + groupName + ']';
    }

}
