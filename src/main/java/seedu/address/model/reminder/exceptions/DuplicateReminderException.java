package seedu.address.model.reminder.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicateReminderException extends DuplicateDataException {
    public DuplicateReminderException() {
        super("Operation would result in duplicate reminders");
    }
}
