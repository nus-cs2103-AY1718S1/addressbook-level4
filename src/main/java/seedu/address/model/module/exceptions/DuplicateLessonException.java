package seedu.address.model.module.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author junming403
/**
 * Signals that the operation will result in duplicate Lesson objects.
 */
public class DuplicateLessonException extends DuplicateDataException {
    public DuplicateLessonException() {
        super("Operation would result in duplicate lesson");
    }
}
