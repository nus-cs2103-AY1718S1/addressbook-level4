package seedu.address.model.user.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author quanle1994

/**
 * Signals that the operation will result in duplicate User objects.
 */
public class DuplicateUserException extends DuplicateDataException {
    public DuplicateUserException() {
        super("User exists");
    }
}
