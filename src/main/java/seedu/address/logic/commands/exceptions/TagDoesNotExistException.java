package seedu.address.logic.commands.exceptions;
/**
 * Represents an error which occurs when Tag does not exist.
 */
public class TagDoesNotExistException extends Exception {
    public TagDoesNotExistException(String message) {
        super(message);
    }
}
