package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Changes a user's password
 */
public class ChangePasswordCommand extends Command {
    public static final String COMMAND_WORD = "changepw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes user's password.\n"
            + "format:" + " " + COMMAND_WORD + " " + "username" + " " + "old password" + " " + "new password";

    public static final String MESSAGE_CHANGE_SUCCESS = "Password changed successfully!";
    public static final String MESSAGE_WRONG_CREDENTIALS = "Invalid Credentials!";

    private final String username;
    private final String oldPw;
    private final String newPw;

    public ChangePasswordCommand(String username, String oldPw, String newPw) {
        this.username = username;
        this.oldPw = oldPw;
        this.newPw = newPw;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (model.getUserPrefs().changePassword(username, oldPw, newPw)) {
            return new CommandResult(MESSAGE_CHANGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_WRONG_CREDENTIALS);
        }
    }
}
