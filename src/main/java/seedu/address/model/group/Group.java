package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author kengying
/**
 * Represents a Person's group in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGroup(String)}
 */
public class Group implements Comparable<Group>, Comparator<Group> {

    public static final String MESSAGE_GROUP_CONSTRAINTS =
            "Person groups should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String GROUP_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String groupName;

    /**
     * Validates given group.
     *
     * @throws IllegalValueException if given group address string is invalid.
     */
    public Group(String group) throws IllegalValueException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!isValidGroup(trimmedGroup)) {
            throw new IllegalValueException(MESSAGE_GROUP_CONSTRAINTS);
        }
        this.groupName = trimmedGroup;
    }

    public Group(Group group) throws IllegalValueException {
        this(group.toString());
    }

    /**
     * Returns if a given string is a valid person group.
     */
    public static boolean isValidGroup(String test) {
        return test.matches(GROUP_VALIDATION_REGEX);
    }

    public int compareTo(Group g) {
        return this.groupName.compareTo(g.groupName);
    }

    public int compare(Group a, Group b) {
        return a.groupName.compareTo(b.groupName);
    }

    @Override
    public String toString() {
        return groupName;
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

}
