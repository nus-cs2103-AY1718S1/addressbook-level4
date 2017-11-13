package seedu.address.model.alias.exceptions;

/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class UnknownCommandException extends Exception {
    public UnknownCommandException(String message) {
        super(message);
    }
}
