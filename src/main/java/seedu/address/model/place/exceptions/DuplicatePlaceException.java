package seedu.address.model.place.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Place objects.
 */
public class DuplicatePlaceException extends DuplicateDataException {
    public DuplicatePlaceException() {
        super("Operation would result in duplicate places");
    }
}
