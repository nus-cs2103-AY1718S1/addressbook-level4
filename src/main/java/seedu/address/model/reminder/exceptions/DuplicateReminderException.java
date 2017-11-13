package seedu.address.model.reminder.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author junyango

/**
 * Signals that the operation will result in duplicate Reminder objects.
 */
public class DuplicateReminderException extends DuplicateDataException {
    public static final String MESSAGE = "Operation would result in duplicate reminder";

    public DuplicateReminderException() {
        super(MESSAGE);
    }
}
