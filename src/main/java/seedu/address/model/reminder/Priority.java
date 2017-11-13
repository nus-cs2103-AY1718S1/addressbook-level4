package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author RonakLakhotia
/**
 * Represents a Reminders priority level, which can be either high, medium or low.
 */

public class Priority {

    public static final String PRIORITY_CONSTRAINTS =
            "Priority must have one of the three values which are - High, Low, and Medium";

    public final String priority;

    public Priority(String priority) throws IllegalValueException {
        requireNonNull(priority);


        if (!isValidPriority(priority)) {
            throw new IllegalValueException(PRIORITY_CONSTRAINTS);
        }
        this.priority = priority;
    }

    /**
     * Returns true if a given string is a valid Priority.
     */
    public static boolean isValidPriority(String priority) {


        if (!(priority.equalsIgnoreCase("Priority Level: High")
                || priority.equalsIgnoreCase("Priority Level: Medium")
                || priority.equalsIgnoreCase("Priority Level: Low"))) {

            return false;
        }
        return true;
    }

    /**
     * Returns the priority Level of Reminder.
     */
    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return priority;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priority.equals(((Priority) other).priority)); // state check
    }

    @Override
    public int hashCode() {
        return priority.hashCode();
    }
}
