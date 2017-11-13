package seedu.address.logic.parser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser when handling arguments.
 */
public class ParseArgsException extends IllegalValueException {

    public ParseArgsException(String message) {
        super(message);
    }

    public ParseArgsException(String message, Throwable cause) {
        super(message, cause);
    }
}
