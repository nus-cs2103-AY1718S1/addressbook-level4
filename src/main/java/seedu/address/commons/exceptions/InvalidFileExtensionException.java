package seedu.address.commons.exceptions;

/**
 * Signals that a given file extension does not fulfill some constraints
 */
public class InvalidFileExtensionException extends Exception {

    public InvalidFileExtensionException() {
        super();
    }

    public InvalidFileExtensionException(String message) {
        super(message);
    }
}
