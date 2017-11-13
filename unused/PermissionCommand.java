package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author LimYangSheng-unused
// Code was not used as requesting user permission before execution was not advised for CLI applications.
/**
 * Represents a command that requires user's permission before proceeding.
 */
public abstract class PermissionCommand extends Command {
    /**
     * Executes the command that requires a user's reply and returns the result.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult executeAfterUserPermission(boolean userPermission) throws CommandException;
}
