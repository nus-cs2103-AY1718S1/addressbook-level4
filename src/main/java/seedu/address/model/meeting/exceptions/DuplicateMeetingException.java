package seedu.address.model.meeting.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Meeting objects.
 */
public class DuplicateMeetingException extends DuplicateDataException {

    public DuplicateMeetingException() {
            super("Operation would result in duplicate meetings");
    }
}
