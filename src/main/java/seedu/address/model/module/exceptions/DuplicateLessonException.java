package seedu.address.model.module.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicateLessonException extends DuplicateDataException {
    public DuplicateLessonException() {
        super("Operation would result in duplicate lesson");
    }
}
