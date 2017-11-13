package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.currentuser.CurrentUserDetails;

/**
 * Gets the user name of the current user
 */
public class CurrentUserCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "currentuser";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the user name of the currently logged in user.";
    public static final String MESSAGE_SUCCESS = "Current User is: %1$s";

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        return new CommandResult(String.format(MESSAGE_SUCCESS, new CurrentUserDetails().getUserId()));
    }
}
