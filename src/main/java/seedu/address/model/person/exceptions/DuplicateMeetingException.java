package seedu.address.model.person.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author Sri-vatsa
/**
 * Raises exception when duplicate meetings are added
 */
public class DuplicateMeetingException extends DuplicateDataException{
    public DuplicateMeetingException() {
        super("Operation would result in duplicate meetings");
    }
}
