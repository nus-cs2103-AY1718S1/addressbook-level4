package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;

import javafx.collections.ObservableList;
import seedu.address.commons.util.digestutil.HashDigest;
import seedu.address.commons.util.digestutil.HexCode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.currentuser.CurrentUserDetails;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.user.exceptions.UserNotFoundException;

//@@author quanle1994

/**
 * Log the user in.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "lgi";
    public static final Object MESSAGE_USAGE = COMMAND_WORD + ": User logs in using a pre-registered account. "
            + "Parameters: "
            + PREFIX_USERID + "USER ID "
            + PREFIX_PASSWORD + "PASSWORD";
    public static final String MESSAGE_SUCCESS = "Log In Successful";
    public static final String MESSAGE_ERROR_NO_USER = "User does not exist";
    public static final String MESSAGE_LOGIN_ERROR = "Log out first before logging in";
    private String userId;
    private String passwordText;

    public LoginCommand(String userId, String passwordText) {
        this.userId = userId;
        this.passwordText = passwordText;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        if (!(new CurrentUserDetails().getUserId().equals("PUBLIC"))) {
            throw new CommandException(MESSAGE_LOGIN_ERROR);
        }

        byte[] userNameHash = new HashDigest().getHashDigest(userId);
        String userNameHex = new HexCode().getHexFormat(new String(userNameHash));
        String saltText = "";
        try {

            String saltHex = model.retrieveSaltFromStorage(userNameHex);
            saltText = new HexCode().hexStringToByteArray(saltHex);
            byte[] saltedPassword = new HashDigest().getHashDigest(saltText + passwordText);
            String saltedPasswordHex = new HexCode().getHexFormat(new String(saltedPassword));

            model.getUserFromIdAndPassword(userNameHex, saltedPasswordHex);

            model.encryptPublic(false);

            ObservableList<ReadOnlyPerson> list = model.getListLength();
            model.emptyPersonList(list);

            model.decrypt(userNameHex.substring(0, 10), saltText + passwordText);
            model.refreshAddressBook();

            new CurrentUserDetails().setCurrentUser(userId, userNameHex, saltText, passwordText);
        } catch (UserNotFoundException e) {
            throw new CommandException(MESSAGE_ERROR_NO_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
