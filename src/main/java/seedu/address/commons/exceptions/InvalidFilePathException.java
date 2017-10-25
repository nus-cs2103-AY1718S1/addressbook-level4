package seedu.address.commons.exceptions;

/**
 * Signals that a given file path does not fulfill some requirements
 */
public class InvalidFilePathException extends Exception {
    public InvalidFilePathException() {
        super();
    }

    public InvalidFilePathException(String message) {
        super(message);
    }
}
