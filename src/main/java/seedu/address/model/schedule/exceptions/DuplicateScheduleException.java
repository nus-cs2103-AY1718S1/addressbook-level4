package seedu.address.model.schedule.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Schedule objects.
 */
public class DuplicateScheduleException extends DuplicateDataException {
    public DuplicateScheduleException() {
        super("Operation would result in duplicate schedules");
    }
}
