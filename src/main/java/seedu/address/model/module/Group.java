package seedu.address.model.module;

import seedu.address.commons.exceptions.IllegalValueException;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGroup(String)}
 */
public class Group {

    public static final String MESSAGE_GROUP_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 1 digits long";
    public static final String GROUP_VALIDATION_REGEX = "\\d";
    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Group(String group) throws IllegalValueException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!isValidGroup(trimmedGroup)) {
            throw new IllegalValueException(MESSAGE_GROUP_CONSTRAINTS);
        }
        this.value = trimmedGroup;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidGroup(String test) {
        return test.matches(GROUP_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group // instanceof handles nulls
                && this.value.equals(((Group) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
