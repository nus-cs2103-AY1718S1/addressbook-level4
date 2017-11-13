package seedu.address.logic.parser.exceptions;

//@@author Juxarius

/**
 * To signal a request for autofilling prefixes in add insurance command due to the multitude of prefixes required
 */
public class MissingPrefixException extends ParseException {
    /**
     * Signal to inform autofilling of prefixes and/or re positioning of caret
     * @param message
     */
    public MissingPrefixException(String message) {
        super(message);
    }
}
