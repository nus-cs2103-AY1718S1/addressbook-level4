package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
//@@author RonakLakhotia
/**
 * Represents a Reminder's details
 * Guarantees: immutable; is valid as declared in {@link #isValidDetail(String)}
 */
public class ReminderDetails {

    public static final String MESSAGE_REMINDER_CONSTRAINTS =
            "Reminders should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the Reminder must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String details;

    /**
     * Validates given details.
     *
     * @throws IllegalValueException if given details string is invalid.
     */
    public ReminderDetails(String details) throws IllegalValueException {
        requireNonNull(details);
        String trimmedDetails = details.trim();
        if (!isValidDetail(trimmedDetails)) {
            throw new IllegalValueException(MESSAGE_REMINDER_CONSTRAINTS);
        }
        this.details = trimmedDetails;
    }

    /**
     * Returns true if a given string is valid
     */
    public static boolean isValidDetail(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return details;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderDetails // instanceof handles nulls
                && this.details.equals(((ReminderDetails) other).details)); // state check
    }

    @Override
    public int hashCode() {
        return details.hashCode();
    }

}
