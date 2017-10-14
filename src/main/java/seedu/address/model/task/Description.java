package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a person's task in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Task descriptions should not consist of only non-alphanumeric characters and should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    
    public final String taskDescription;

    /**
     * Validates given task description.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!isValidDescription(trimmedDescription)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.taskDescription = trimmedDescription;
    }

    /**
     * Returns true if a given string is a valid task description.
     */
    public static boolean isValidDescription(String test) {
        
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return taskDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.taskDescription.equals(((Description) other).taskDescription)); // state check
    }

    @Override
    public int hashCode() {
        return taskDescription.hashCode();
    }
}
