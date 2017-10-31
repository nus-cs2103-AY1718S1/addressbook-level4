package seedu.address.model.module.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate BookedSlot objects.
 */
public class DuplicateBookedSlotException extends DuplicateDataException {

    public DuplicateBookedSlotException() {
        super("Operation would result in duplicate BookedSlot");
    }

}

