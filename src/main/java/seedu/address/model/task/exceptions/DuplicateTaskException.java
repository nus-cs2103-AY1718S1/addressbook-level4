package seedu.address.model.task.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author deep4k
/**
 * Signals that the operation will result in duplicate Tasks objects.
 */
public class DuplicateTaskException extends DuplicateDataException {
    public DuplicateTaskException() {
        super("Operation would result in duplicate tasks");
    }
}
