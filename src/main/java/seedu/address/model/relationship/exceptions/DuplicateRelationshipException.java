package seedu.address.model.relationship.exceptions;

/**
 * Signals that an operation would have violated the 'no duplicates' property of the list.
 */
public class DuplicateRelationshipException extends Exception {
    public DuplicateRelationshipException() {
        super("Operation would result in duplicate relationships");
    }
}
