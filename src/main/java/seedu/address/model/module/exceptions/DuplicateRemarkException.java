package seedu.address.model.module.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author junming403
/**
 * Signals that the operation will result in duplicate Remark objects.
 */
public class DuplicateRemarkException extends DuplicateDataException {
    public DuplicateRemarkException() {
        super("Operation would result in duplicate remark");
    }
}
