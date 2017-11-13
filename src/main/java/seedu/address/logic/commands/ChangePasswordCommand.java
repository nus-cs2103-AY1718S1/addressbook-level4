package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONFIRM_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import com.google.common.hash.Hashing;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.UserPrefs;

//@@author charlesgoh
/**
 * Changes user's password provided old password is correct and new passwords match.
 */
public class ChangePasswordCommand extends Command {
    public static final String COMMAND_WORD = "changepassword";
    public static final String COMMAND_ALIAS = "cpw";

    public static final String MESSAGE_SUCCESS = "Password changed successfully";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes user password. \n"
            + PREFIX_PASSWORD + "PASSWORD "
            + PREFIX_NEW_PASSWORD + "NEWPASSWORD "
            + PREFIX_CONFIRM_PASSWORD + "CONFIRMPASSWORD \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PASSWORD + "password "
            + PREFIX_NEW_PASSWORD + "mynewpassword111 " + PREFIX_CONFIRM_PASSWORD + "mynewpassword111\n"
            + "Example 2: " + COMMAND_ALIAS + " " + PREFIX_PASSWORD + "password "
            + PREFIX_NEW_PASSWORD + "mynewpassword111 " + PREFIX_CONFIRM_PASSWORD + "mynewpassword111\n";

    public static final String MESSAGE_INVALID_INPUT = "Invalid Input.\n";

    public static final String MESSAGE_OLD_NEW_PS_SAME = "New password must be different from your old password";
    public static final String MESSAGE_ERROR_OCCURED = "An error occured. Please try again.\n";
    public static final String MESSAGE_PASSWORD_INCORRECT = "Your password is incorrect. Please try again.\n";
    public static final String MESSAGE_PASSWORD_CONFIRMATION_INCORRECT = "Your new password and confirmation password "
            + "do not match. Please try again\n";

    private final Logger logger = LogsCenter.getLogger(ChangePasswordCommand.class);

    private String oldPassword;
    private String newPassword;
    private String confirmationPassword;

    /**
     * Takes in old password, new password and confirmation password from parser and creates a new
     * ChangePasswordCommand object
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    public ChangePasswordCommand(String oldPassword, String newPassword, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmationPassword = confirmPassword;
    }

    /**
     * Forward hashes string using SHA256 encryption and returns hashed string
     * @param argument
     */
    private String forwardHash(String argument) {
        return Hashing.sha256().hashString(argument, StandardCharsets.UTF_8).toString();
    }

    /**
     * Forward hashes the user input password and checks if it matches with the encrypted password saved
     */
    private boolean isOldPasswordCorrect() {
        String forwardHashedInputPassword = forwardHash(oldPassword);
        String forwardHashActualPassword;
        UserPrefs userPrefs = model.getUserPrefs();
        forwardHashActualPassword = userPrefs.getAddressBookEncryptedPassword();
        if (forwardHashActualPassword.equals(forwardHashedInputPassword)) {
            logger.info("Actual password and input password matches");
            return true;
        } else {
            logger.warning("Actual password and input password do not match");
            return false;
        }
    }

    /**
     * Takes new input passwords and checks them against one another.
     */
    private boolean isNewPasswordInputsSame() {
        return newPassword.equals(confirmationPassword);
    }

    /**
     * Checks if old password and new password are the same
     */
    private boolean isOldAndNewPasswordTheSame() {
        return oldPassword.equals(newPassword);
    }

    @Override
    public CommandResult execute() throws CommandException {
        // Case where old password is incorrect
        if (!isOldPasswordCorrect()) {
            logger.warning("Password is incorrect. Note: Default password is 'password' ");
            throw new CommandException(MESSAGE_PASSWORD_INCORRECT);
        }

        // Case where new password and confirmation password do not match
        if (!isNewPasswordInputsSame()) {
            logger.warning("New password and confirmation password do not match");
            throw new CommandException(MESSAGE_PASSWORD_CONFIRMATION_INCORRECT);
        }

        // Case where old and new passwords are the same
        if (isOldAndNewPasswordTheSame()) {
            logger.warning("Old password and new password cannot be the same");
            throw new CommandException(MESSAGE_OLD_NEW_PS_SAME);
        }

        // Case where user input passes both checks. Password is changed and UserPrefs saved
        UserPrefs userPrefs;

        // Get user prefs file
        userPrefs = model.getUserPrefs();

        // Set new password to user prefs
        userPrefs.setAddressBookEncryptedPassword(newPassword);

        // Logs new password and saved password for debugging purposes
        String hashedNewPassword = forwardHash(newPassword);
        String userPrefsHashedPassword = userPrefs.getAddressBookEncryptedPassword();
        logger.info("New Password: " + newPassword
                + "\nEncrypted New Password: " + hashedNewPassword
                + "\nEncrypted Password From UserPrefs:" + userPrefsHashedPassword
                + "\nCommand's Password and UserPrefs saved password matches: "
                + Boolean.toString(hashedNewPassword.equals(userPrefsHashedPassword)) + "\n");

        // Return command result
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
