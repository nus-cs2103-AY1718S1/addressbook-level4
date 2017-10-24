package seedu.address.commons.exceptions;

/**
 * Signals that a given file name or folder name contains invalid characters
 */
public class InvalidNameException extends InvalidFilePathException {
    public InvalidNameException() {
        super();
    }
}
