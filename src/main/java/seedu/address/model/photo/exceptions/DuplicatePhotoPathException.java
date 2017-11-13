package seedu.address.model.photo.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate PhotoPath objects.
 */
public class DuplicatePhotoPathException extends DuplicateDataException {
    public DuplicatePhotoPathException() {
        super("Operation would result in duplicate photo paths.");
    }
}
