package seedu.address.commons.exceptions;

//@@author low5545
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
