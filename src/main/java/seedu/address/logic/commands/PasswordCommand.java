package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_PASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASS;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.logic.commands.commandmode.PasswordMode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.SecurityManager;
//@@author syy94

/**
 * Adds, removes or edit password for the application.
 */
public class PasswordCommand extends Command {

    public static final String COMMAND_WORD = "password";
    public static final String COMMAND_ALIAS = "pwd";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds, removes or changes the password required to use "
            + "the application.\n"
            + "Set Password: " + COMMAND_WORD + " " + PREFIX_PASS + "PASS\n"
            + "Change Password: " + COMMAND_WORD + " " + PREFIX_PASS + "PASS" + " " + PREFIX_NEW_PASS + "NEW_PASS\n"
            + "Clear Password: " + COMMAND_WORD + " " + PREFIX_PASS + "PASS" + " " + PREFIX_CLEAR_PASS;

    public static final String MESSAGE_FILE_NOT_FOUND = "Password file not found.";
    public static final String MESSAGE_WRONG_PASS = "Wrong password.";
    public static final String MESSAGE_CHANGED_PASS = "Password Changed";
    public static final String MESSAGE_PASS_EXISTS = "Password Exists. To change password, use " + COMMAND_WORD + " "
            + PREFIX_PASS + "[existing password] "
            + PREFIX_NEW_PASS + "[new password] ";
    public static final String MESSAGE_CLEARED_PASS = "Password Cleared";
    public static final String MESSAGE_SET_PASS = "Password Set";
    public static final String MESSAGE_PASS_NOT_CHANGED = "Password not changed.";
    public static final String MESSAGE_NO_PASS_TO_CLEAR = "No existing password to clear";
    public static final String MESSAGE_NO_PASS_TO_CHANGE = "No existing password to change";
    public static final String MESSAGE_NO_SUCH_ALGORITHM = "NoSuchAlgorithmException should not be reached. \n"
            + "Contact the developers at: \n"
            + "https://github.com/CS2103AUG2017-F11-B1/main/issues";

    final PasswordMode mode;

    public PasswordCommand(PasswordMode mode) {
        this.mode = mode;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute() throws CommandException {
        try {
            return mode.execute();
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (NoSuchAlgorithmException e) {
            throw new CommandException(MESSAGE_NO_SUCH_ALGORITHM);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PasswordCommand // instanceof handles nulls
                && this.mode.equals(((PasswordCommand) other).mode)); // state check
    }

    /**
     * PasswordMode to set Password
     */
    public static class SetPass extends PasswordMode {
        public SetPass(String pass) {
            super(pass);
        }

        @Override
        public CommandResult execute() throws IOException, NoSuchAlgorithmException, CommandException {
            if (passExists()) {
                throw new CommandException(MESSAGE_PASS_EXISTS);
            } else {
                SecurityManager.savePass(getPass());
                return new CommandResult(MESSAGE_SET_PASS);
            }
        }
    }

    /**
     * PasswordMode to clear Password
     */
    public static class ClearPass extends PasswordMode {
        public ClearPass(String pass) {
            super(pass);
        }

        @Override
        public CommandResult execute() throws IOException, NoSuchAlgorithmException, CommandException {
            if (passExists()) {
                try {
                    SecurityManager.removePass(getPass());
                } catch (WrongPasswordException e) {
                    throw new CommandException(MESSAGE_WRONG_PASS);
                }
                return new CommandResult(MESSAGE_CLEARED_PASS);
            } else {
                throw new CommandException(MESSAGE_NO_PASS_TO_CLEAR);
            }
        }
    }

    /**
     * PasswordMode to change Password
     */
    public static class ChangePass extends PasswordMode {

        private String newPass;

        public ChangePass(String pass, String newPass) {
            super(pass);
            this.newPass = newPass;
        }

        @Override
        public CommandResult execute() throws IOException, NoSuchAlgorithmException, CommandException {
            if (passExists()) {
                if (SecurityManager.checkPass(getPass())) {
                    SecurityManager.savePass(newPass);
                    return new CommandResult(MESSAGE_CHANGED_PASS);
                } else {
                    throw new CommandException(MESSAGE_WRONG_PASS + "\n" + MESSAGE_PASS_NOT_CHANGED);
                }
            } else {
                throw new CommandException(MESSAGE_NO_PASS_TO_CHANGE);
            }
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof ChangePass // instanceof handles nulls
                    && this.newPass.equals(((ChangePass) other).newPass)
                    && super.equals(other)); // state check
        }
    }
}
