package seedu.address.model.parcel.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Parcel objects.
 */
public class DuplicateParcelException extends DuplicateDataException {
    public DuplicateParcelException() {
        super("Operation would result in duplicate parcels");
    }
}
