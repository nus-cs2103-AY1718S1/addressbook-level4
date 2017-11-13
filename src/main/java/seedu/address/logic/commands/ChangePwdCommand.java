package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author moomeowroar
/**
 * Sends a message to the contact through email
 */

public class ChangePwdCommand extends Command {

    public static final String COMMAND_WORD = "changepwd";
    public static final String COMMAND_ALIAS = "cpwd";
    public static final String COMMAND_HELP = "changepwd NEW_PASSWORD OLD_PASSWORD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes password. \n"
            + COMMAND_WORD + " <new_password> <old_password>";


    public static final String MESSAGE_CHANGE_FAILURE = "Wrong password.";
    public static final String MESSAGE_CHANGE_SUCCESS = "Password changed successfully.";

    private final String oldPwd;
    private final String newPwd;

    /**
     * @param String Name of setting to change
     */
    public ChangePwdCommand(String oldPwd, String newPwd) {
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (model.getUserPrefs().changePwd(oldPwd, newPwd)) {
            return new CommandResult(MESSAGE_CHANGE_SUCCESS);
        } else {
            throw new CommandException(MESSAGE_CHANGE_FAILURE);
        }
    }


}
