package seedu.address.commons.exceptions;

//@@author low5545
/**
 * Signals that a given file extension does not fulfill some constraints
 */
public class InvalidFileExtensionException extends InvalidFilePathException {

    public InvalidFileExtensionException() {
        super();
    }

    public InvalidFileExtensionException(String message) {
        super(message);
    }
}
