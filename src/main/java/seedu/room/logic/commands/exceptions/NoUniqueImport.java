package seedu.room.logic.commands.exceptions;
//@@author blackroxs
/**
 * Represents an error which occurs during execution of a Sort Command Execution.
 */
public class NoUniqueImport extends Exception {
    public NoUniqueImport(String message) {
        super(message);
    }
}
