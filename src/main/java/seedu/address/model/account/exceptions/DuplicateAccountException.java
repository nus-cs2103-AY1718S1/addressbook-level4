package seedu.address.model.account.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 *
 */
public class DuplicateAccountException extends DuplicateDataException {
    public DuplicateAccountException() {
        super("Operation would result in duplicate Account");
    }
}

