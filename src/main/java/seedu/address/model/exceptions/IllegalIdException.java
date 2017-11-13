package seedu.address.model.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author Sri-vatsa
/***
 * Signals that a particular person id does not exist in address book
 */
public class IllegalIdException extends IllegalValueException {

    public IllegalIdException(String message) {
        super (message);
    }
}
