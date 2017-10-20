package seedu.address.model.module.exceptions;

/**
 * Signals that the operation will result in duplicate BookedSlot objects.
 */
import seedu.address.commons.exceptions.DuplicateDataException;

public class DuplicateBookedSlotException extends DuplicateDataException {

    public DuplicateBookedSlotException() {
        super("Operation would result in duplicate BookedSlot");
    }

}

