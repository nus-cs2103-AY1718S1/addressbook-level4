package seedu.address.model.person.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Exception for duplicate groups in the unique group list
 */

public class DuplicateGroupException extends DuplicateDataException {
    public DuplicateGroupException () {
        super("Operation would result in duplicate groups");
    }
}
