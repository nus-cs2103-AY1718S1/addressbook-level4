package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

    /**
     * Changes a user's password
     */
public class ChangeUsernameCommand extends Command {
    public static final String COMMAND_WORD = "changeuser";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes a user's username.\n"
        + "format:" + " " + COMMAND_WORD + " " + "old username" + " " + "new username" + " " + "password";

    public static final String MESSAGE_CHANGE_SUCCESS = "Username changed successfully!";
    public static final String MESSAGE_WRONG_CREDENTIALS = "Invalid Credentials!";

    private final String newUsername;
    private final String oldUsername;
    private final String password;

    public ChangeUsernameCommand(String oldUsername, String newUsername, String password) {
        this.oldUsername = oldUsername;
        this.newUsername = newUsername;
        this.password = password;
        }

    @Override
    public CommandResult execute() throws CommandException {
        if (model.getUserPrefs().changeUsername(oldUsername, newUsername, password)) {
            return new CommandResult(MESSAGE_CHANGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_WRONG_CREDENTIALS);
        }
    }
}
