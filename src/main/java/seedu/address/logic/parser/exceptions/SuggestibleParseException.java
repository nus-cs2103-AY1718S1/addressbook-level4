package seedu.address.logic.parser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an invalid argument error encountered by a parser.
 * Used to handle suggestions.
 */
public class SuggestibleParseException extends IllegalValueException {

    public SuggestibleParseException(String message) {
        super(message);
    }

    public SuggestibleParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
