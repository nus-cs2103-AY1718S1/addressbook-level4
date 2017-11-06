package seedu.address.model.task.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * This is an exception for duplicate tasks operations
 */
public class DuplicateTaskException extends DuplicateDataException {

    /**
     * default constructor
     */
    public DuplicateTaskException() {
        super("Operation would result in duplicate tasks");
    }
}
