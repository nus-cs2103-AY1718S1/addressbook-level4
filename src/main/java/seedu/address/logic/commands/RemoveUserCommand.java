package seedu.address.logic.commands;

import seedu.address.logic.commands.digestutil.HashDigest;
import seedu.address.logic.commands.digestutil.HexCode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.currentuser.CurrentUserDetails;
import seedu.address.commons.util.encryption.FileEncryptor;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.model.user.exceptions.UserNotFoundException;

/**
 * Remove user from Account file
 */
public class RemoveUserCommand extends Command {
    public static final String COMMAND_WORD = "remove";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove the user from the Account storage.\n"
            + "Parameters: u/USERNAME, p/PASSWORD, r/REMOVE_CONTACT\n"
            + "USERNAME is a string and is the user id of the account you want to remove\n"
            + "PASSWORD is a string associated with that user. It must match with the password stored locally\n"
            + "REMOVE_CONTACT is either a Y or N, Y means the contacts associated to that user will be deleted, and "
            + "N means the contacts associated to that user will be released to accessible by the public."
            + "Example: " + COMMAND_WORD + " u/lequangquan p/123123 r/Y";
    public static final String MESSAGE_REMOVE_USER_SUCCESS = "Removed user: %1$s";
    private static final String MESSAGE_USER_NOT_FOUND = "The user credentials provided do not match our "
            + "database.";
    private static final String MESSAGE_ENCRYPTION_ERROR = "Decryption Failed";
    private static final String MESSAGE_USER_LOGGED_IN = "You are still logged in as \"%1$s\"! Logged out first " +
            "before removing "
            + "the user";
    private String userName;
    private String password;
    private boolean cascade;

    public RemoveUserCommand(String userName, String password, boolean cascade) {
        this.userName = userName;
        this.password = password;
        this.cascade = cascade;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() throws CommandException, DuplicateUserException {
        if (!(new CurrentUserDetails().getUserId().equals("PUBLIC"))){
            throw new CommandException(String.format(MESSAGE_USER_LOGGED_IN, new CurrentUserDetails().getUserId()));
        }
        try {
            byte[] userNameHash = new HashDigest().getHashDigest(userName);
            String userNameHex = new HexCode().getHexFormat(new String(userNameHash));

            String saltHex = model.retrieveSaltFromStorage(userNameHex);
            String saltText = new HexCode().hexStringToByteArray(saltHex);
            byte[] saltedPassword = new HashDigest().getHashDigest(saltText + password);
            String saltedPasswordHex = new HexCode().getHexFormat(new String(saltedPassword));

            model.deleteUser(userNameHex, saltedPasswordHex);
            if (cascade) {
                model.deleteEncryptedContacts(userNameHex.substring(0, 10));
            } else {
                FileEncryptor.decryptFile(userNameHex.substring(0, 10), saltText + password);
                model.releaseEncryptedContact(userNameHex.substring(0, 10));
            }
        } catch (UserNotFoundException unfe) {
            throw new CommandException(MESSAGE_USER_NOT_FOUND);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_ENCRYPTION_ERROR);
        }
        new SaveCommand().execute();
        return new CommandResult(String.format(MESSAGE_REMOVE_USER_SUCCESS, userName));
    }
}
