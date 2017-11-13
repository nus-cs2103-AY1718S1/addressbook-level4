package seedu.address.model.task;
//@@author Esilocke

import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents a task priority in the address book.
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Task priorities must be an integer from 0 to 5, inclusive, where 5 represents the highest priority";
    public static final String[] PRIORITY_TEXT_STRINGS = {"", "Lowest", "Low", "Medium", "High", "Highest"};

    public static final int PRIORITY_LOWER_BOUND = 0;
    public static final int PRIORITY_UPPER_BOUND = 5;
    public static final String PRIORITY_VALIDATION_REGEX = "[\\d].*";
    public static final String PRIORITY_PLACEHOLDER_VALUE = "";
    public final int value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        if (priority == null || priority.equals(PRIORITY_PLACEHOLDER_VALUE)) {
            this.value = 0;
            return;
        }
        String trimmedPriority = priority.trim();
        try {
            this.value = Integer.parseInt(trimmedPriority);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        if (!isValidPriority(trimmedPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid task priority.
     */
    public static boolean isValidPriority(String test) {
        if (test.equals(PRIORITY_PLACEHOLDER_VALUE)) {
            return true;
        } else if (!test.matches(PRIORITY_VALIDATION_REGEX)) {
            return false;
        } else {
            int intTest = Integer.parseInt(test);
            return isWithinBounds(intTest);
        }
    }

    /**
     * Returns true if the value is within the upper and lower bounds of priority
     */
    public static boolean isWithinBounds(int test) {
        return test <= PRIORITY_UPPER_BOUND && test >= PRIORITY_LOWER_BOUND;
    }

    @Override
    public String toString() {
        return PRIORITY_TEXT_STRINGS[value];
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value == ((Priority) other).value); // state check
    }
}
