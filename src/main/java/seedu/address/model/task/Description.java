package seedu.address.model.task;
//@@author Esilocke

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task description in the address book.
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Task descriptions can be in any format";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "[^\\s].*";
    public static final String DESCRIPTION_PLACEHOLDER_VALUE = "";
    public final String value;

    /**
     * Validates given description.
     *
     * @throws IllegalValueException if given description string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        if (description == null) {
            this.value = DESCRIPTION_PLACEHOLDER_VALUE;
            return;
        }
        String trimmedDescription = description.trim();
        if (!isValidDescription(trimmedDescription)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.value = trimmedDescription;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.equals(DESCRIPTION_PLACEHOLDER_VALUE) || test.matches(DESCRIPTION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
