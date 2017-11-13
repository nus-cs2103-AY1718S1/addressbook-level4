package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;

import java.util.concurrent.ThreadLocalRandom;

import seedu.address.commons.util.digestutil.HashDigest;
import seedu.address.commons.util.digestutil.HexCode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.currentuser.CurrentUserDetails;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.DuplicateUserException;

//@@author quanle1994

/**
 * Create an account and encrypt the addressbook.xml with that account
 */
public class LockCommand extends Command {
    public static final String COMMAND_WORD = "lock";
    public static final String COMMAND_ALIAS = "lo";
    public static final Object MESSAGE_USAGE = COMMAND_WORD + ": Locks the current address book with a user account. "
            + "Parameters: "
            + PREFIX_USERID + "USER ID "
            + PREFIX_PASSWORD + "PASSWORD";
    public static final String MESSAGE_SUCCESS = "Account is created and your Address Book is locked with your "
            + "password";
    public static final String MESSAGE_EXISTING_USER = "User already exists";
    private static final int SALT_MIN = 0;
    private static final int SALT_MAX = 1000000;
    private String userId;
    private String passwordText;

    public LockCommand(String userId, String passwordText) {
        this.userId = userId;
        this.passwordText = passwordText;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() throws CommandException, DuplicateUserException {
        requireNonNull(model);

        byte[] uIdDigest = new HashDigest().getHashDigest(userId);
        String saltText = "" + ThreadLocalRandom.current().nextInt(SALT_MIN, SALT_MAX + 1);
        byte[] pwDigest = new HashDigest().getHashDigest(saltText + passwordText);
        String hexUidDigest = new HexCode().getHexFormat(new String(uIdDigest));
        String hexSalt = new HexCode().getHexFormat(saltText);
        String hexPassword = new HexCode().getHexFormat(new String(pwDigest));
        try {
            model.persistUserAccount(new User(hexUidDigest, hexSalt, hexPassword));
        } catch (DuplicateUserException due) {
            throw new CommandException(MESSAGE_EXISTING_USER);
        }

        try {
            model.encrypt(hexUidDigest.substring(0, 10), saltText + passwordText, false);
            model.encryptPublic(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new CurrentUserDetails().setCurrentUser(this.userId, hexUidDigest, saltText, this.passwordText);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public String getUserId() {
        return userId;
    }

    public String getPasswordText() {
        return passwordText;
    }
}
