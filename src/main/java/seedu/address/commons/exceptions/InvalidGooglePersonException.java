package seedu.address.commons.exceptions;


//@@author philemontan
/**
 * This exception is thrown to indicate an invalid Google Person object in the process of being converted by the
 * GooglePersonConverterUtil
 */
public class InvalidGooglePersonException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public InvalidGooglePersonException(String message) {
        super(message);
    }

}
