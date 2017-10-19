package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;

/**
 * Represents a Group in an address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGroupName(String)}
 */
public class Group {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Group names should contain only "
            + "alphanumeric characters, spaces, underscores and dashes";
    public static final String GROUP_VALIDATION_REGEX = "^[a-zA-Z0-9]([\\w -]*[a-zA-Z0-9])?$";

    public final String groupName;
    private Set<Person> members;

    /**
     * Validates given group name.
     *
     * @throws IllegalValueException if the given group name string is invalid.
     */
    public Group(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidGroupName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.groupName = trimmedName;
        members = new HashSet<>();
    }

    /**
     * Returns true if a given string is a valid tag name.
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



    public void addMember(Person person) {
        this.members.add(person);
    }

    public void deleteMember(Person person) {
        this.members.remove(person);
    }

}
