package seedu.room.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a Sort Command Execution.
 */
public class AlreadySortedException extends Exception {
    public AlreadySortedException(String message) {
        super(message);
    }
}
