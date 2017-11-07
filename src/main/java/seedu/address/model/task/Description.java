package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author chairz
/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String DESCRIPTION_VALIDATION_REGEX = ".+";

    public final String descriptionName;

    /**
     * Validates given description for a task.
     *
     * @throws IllegalValueException if the given description string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        requireNonNull(description);
        String descriptionName = description.trim();
        if (!isValidDescription(descriptionName)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.descriptionName = descriptionName;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.descriptionName.equals(((Description) other).descriptionName)); // state check
    }

    @Override
    public int hashCode() {
        return descriptionName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return descriptionName;
    }
}
