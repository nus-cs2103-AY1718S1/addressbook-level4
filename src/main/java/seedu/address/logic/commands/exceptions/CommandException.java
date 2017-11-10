package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link seedu.address.logic.commands.Command}.
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}
