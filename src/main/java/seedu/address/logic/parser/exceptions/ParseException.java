package seedu.address.logic.parser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends IllegalValueException {

    private String exceptionHeader;


    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(String header, String body, Throwable cause) {
        super(body, cause);
        this.exceptionHeader = header;
    }

    public ParseException(String header, String body) {
        super(body);
        this.exceptionHeader = header;
    }

    public String getExceptionHeader() {
        return exceptionHeader;
    }
}
