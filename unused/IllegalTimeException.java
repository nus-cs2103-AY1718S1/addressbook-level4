package seedu.address.logic.parser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author Sri-vatsa
//No longer required as DatetimeException handles both exceptions in date and time

public class IllegalTimeException extends IllegalValueException {

    public IllegalTimeException(String message) {super (message);}
}
