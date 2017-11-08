package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.util.encryption.FileEncryptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.currentuser.CurrentUserDetails;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.user.exceptions.DuplicateUserException;

/**
 * Log out the current user to "PUBLIC" user
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";
    public static final String COMMAND_ALIAS = "lgo";
    private static final String MESSAGE_SUCCESS = "Logged out successfully!";
    private static final String MESSAGE_LOGOUT_ERROR = "You have not logged in!";
    private static final String MESSAGE_PUBLIC_CONTACTS_DECRYPTION_ERROR = "Cannot decrypt the public contacts";
    private CurrentUserDetails currentUser = new CurrentUserDetails();

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() throws CommandException, DuplicateUserException {
        if (currentUser.getUserId().equals("PUBLIC")) {
            throw new CommandException(MESSAGE_LOGOUT_ERROR);
        }
        try {
            ObservableList<ReadOnlyPerson> list = model.getListLength();
            FileEncryptor.encryptFile(currentUser.getUserIdHex().substring(0, 10), currentUser.getSaltText()
                    + currentUser.getPasswordText(), true);
            model.emptyPersonList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileEncryptor.decryptFile("PUBLIC", "PUBLIC");
            model.refreshAddressBook();
        } catch (Exception e) {
            throw new CommandException(MESSAGE_PUBLIC_CONTACTS_DECRYPTION_ERROR);
        }
        currentUser.setPublicUser();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
