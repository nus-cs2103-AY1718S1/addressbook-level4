package seedu.address.model.alias.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicateAliasException extends DuplicateDataException {
    public DuplicateAliasException(String e) {
        super(e);
    }
}
