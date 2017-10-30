package seedu.address.model.insurance.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author OscarWang114
/**
 * Signals that the operation will result in duplicate Insurance objects.
 */
public class DuplicateInsuranceException extends DuplicateDataException {
    public DuplicateInsuranceException() {
        super("Operation would result in duplicate insurances");
    }
}
