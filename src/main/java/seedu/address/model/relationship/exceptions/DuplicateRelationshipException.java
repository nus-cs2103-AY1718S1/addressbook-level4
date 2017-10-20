package seedu.address.model.relationship.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that an operation would have violated the 'no duplicates' property of the list.
 */
public class DuplicateRelationshipException extends DuplicateDataException {
    public DuplicateRelationshipException() {
        super("Operation would result in duplicate relationships");
    }
}
