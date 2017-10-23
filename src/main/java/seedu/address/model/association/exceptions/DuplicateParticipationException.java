package seedu.address.model.association.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate participation entities.
 */
public class DuplicateParticipationException extends DuplicateDataException {

    public DuplicateParticipationException() {
        super("Operation would result in duplicate participants");
    }
}
