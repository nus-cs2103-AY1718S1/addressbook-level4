package seedu.address.logic.parser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author Sri-vatsa

/**
 * Catches illegal dates and times in user inputs
 */
public class IllegalDateTimeException extends IllegalValueException {

    public IllegalDateTimeException (String message) {
        super (message);
    }

}
