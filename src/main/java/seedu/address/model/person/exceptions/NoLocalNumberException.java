package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Phone objects.
 */

public class NoLocalNumberException extends Exception {
    public NoLocalNumberException() { super("no local number added"); }
}
