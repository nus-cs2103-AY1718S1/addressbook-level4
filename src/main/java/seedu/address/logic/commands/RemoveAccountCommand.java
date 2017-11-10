package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author derickjw
/**
 * Remove the need to log in
 */
public class RemoveAccountCommand extends Command {
    public static final String COMMAND_WORD = "removeLogin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove the need to login \n" + "format:"
            + " " + COMMAND_WORD + " " + "username" + " " + "password";

    public static final String MESSAGE_REMOVE_SUCCESS = "Login removed successfully";
    public static final String MESSAGE_ACCOUNT_ALREADY_REMOVED = "Account does not exist";
    public static final String MESSAGE_INVALID_CREDENTIALS = "Invalid Credentials! Please try again.";

    private final String username;
    private final String password;

    public RemoveAccountCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (model.getUserPrefs().checkUsername("") || model.getUserPrefs().checkPassword("")) {
            return new CommandResult(MESSAGE_ACCOUNT_ALREADY_REMOVED);
        } else {
            if (!model.getUserPrefs().checkUsername(username) || !model.getUserPrefs().checkPassword(password)) {
                return new CommandResult(MESSAGE_INVALID_CREDENTIALS);
            } else if (model.getUserPrefs().checkUsername(username) && model.getUserPrefs().checkPassword(password)) {
                model.getUserPrefs().setDefaultUsername("");
                model.getUserPrefs().setDefaultPassword("");
            }
            return new CommandResult(MESSAGE_REMOVE_SUCCESS);
        }
    }
}
