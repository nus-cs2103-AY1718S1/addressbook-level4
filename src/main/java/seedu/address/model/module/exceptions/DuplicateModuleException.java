package seedu.address.model.module.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Module objects.
 */
public class DuplicateModuleException extends DuplicateDataException {
    public DuplicateModuleException() {
        super("Operation would result in duplicate module");
    }
}
