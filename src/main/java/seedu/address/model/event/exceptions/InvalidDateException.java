//@@author A0162268B
package seedu.address.model.event.exceptions;

/**
 * Signals that the input date does not exist in the gregorian calendar
 */
public class InvalidDateException extends Exception {
    public InvalidDateException() {
        super("Given date does not exist in the gregorian calendar.");
    }
}
