package seedu.address.model.person.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Phone objects.
 */
public class DuplicatePhoneException extends DuplicateDataException {
    public DuplicatePhoneException() {
        super("Operation would result in duplicate phones");
    }
}
