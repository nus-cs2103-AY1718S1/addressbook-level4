package seedu.address.model.alias.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate AliasToken objects.
 */
public class DuplicateTokenKeywordException extends DuplicateDataException {
    public DuplicateTokenKeywordException() {
        super("Operation would result in duplicate tokens with same keyword defined");
    }
}
