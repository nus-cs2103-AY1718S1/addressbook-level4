//@@author cqhchan
package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

import seedu.address.model.account.ReadOnlyAccount;
/**
 *
 */
public class LoginCommand extends Command {


    public static final String COMMAND_WORD = "login";
    public static final String MESSAGE_FAILURE = "Username or Password Incorrect";
    public static final String MESSAGE_SUCCESS = "Login Successful";
    private static String MESSAGE_LOGIN_ACKNOWLEDGEMENT;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login to private Database. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD";

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private ReadOnlyAccount account;

    public LoginCommand(ReadOnlyAccount account) {
        this.account = account;
    }

    @Override
    public CommandResult execute() {

        if (model.checkAccount(account)) {
            try {
                MainApp.getUi().restart(account.getUsername().fullName);
            } catch (Exception e) {
                logger.info("Exception caught" + e.toString());
            }
            return new CommandResult(MESSAGE_SUCCESS);
        }

        return new CommandResult(MESSAGE_FAILURE);

    }
}
