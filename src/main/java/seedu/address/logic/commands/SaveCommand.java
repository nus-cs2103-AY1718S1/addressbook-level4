package seedu.address.logic.commands;

import seedu.address.commons.util.encryption.FileEncryptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.currentuser.CurrentUserDetails;
import seedu.address.model.user.exceptions.DuplicateUserException;

/**
 * Save current contacts to current user
 */
public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    public static final String COMMAND_ALIAS = "sa";
    private static final String MESSAGE_SUCCESS = "Successfully save contacts to user \"%1$s\".";
    private static final String MESSAGE_CANNOT_SAVE_FILE = "Cannot save file";

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() throws CommandException, DuplicateUserException {
        CurrentUserDetails curUser = new CurrentUserDetails();
        String fileName = curUser.getUserIdHex();
        fileName = fileName.substring(0, Math.min(fileName.length(), 10));
        String passPhrase = curUser.getSaltText() + curUser.getPasswordText();
        try {
            FileEncryptor.encryptFile(fileName, passPhrase, false);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_CANNOT_SAVE_FILE);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, curUser.getUserId()));
    }
}
