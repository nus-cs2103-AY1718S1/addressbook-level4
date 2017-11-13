package seedu.address.model.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author Sri-vatsa
/**
 *Signals that an operation would have violated the 'no duplicates' property of the list.
 */
public class DuplicateMeetingException extends DuplicateDataException {
    public DuplicateMeetingException() {
        super("Operation would result in duplicate meetings");
    }
}
