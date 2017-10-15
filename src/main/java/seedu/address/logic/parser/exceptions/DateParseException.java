package seedu.address.logic.parser.exceptions;

/**
 * Represents a parse error encountered when parsing Date.
 */
public class DateParseException extends ParseException {

    public DateParseException(String message) {
        super(message);
    }
}
