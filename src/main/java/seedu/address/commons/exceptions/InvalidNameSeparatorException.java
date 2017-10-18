package seedu.address.commons.exceptions;

/**
 * Signals that a given file path contains invalid name-separator characters
 */
public class InvalidNameSeparatorException extends Exception {
    public InvalidNameSeparatorException(String message) {
        super(message);
    }
}
