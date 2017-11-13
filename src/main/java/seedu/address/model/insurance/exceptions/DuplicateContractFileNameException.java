package seedu.address.model.insurance.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author OscarWang114
/**
 * Signals that the operation will result in duplicate Insurance objects.
 */
public class DuplicateContractFileNameException extends DuplicateDataException {
    public DuplicateContractFileNameException() {
        super("Operation would result in duplicate insurance contract file names");
    }
}
