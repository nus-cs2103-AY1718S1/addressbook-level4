package seedu.address.model.reminder.exceptions;

//@@author junyango

/**
 * Signals that the operation is unable to find the specified reminder.
 */
public class ReminderNotFoundException extends Exception {
    public ReminderNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}

