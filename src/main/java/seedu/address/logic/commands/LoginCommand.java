//@@author cqhchan
package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

import seedu.address.model.credentials.ReadOnlyAccount;
//@@author cqhchan
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

        for (ReadOnlyAccount tempaccount : model.getFilteredAccountList()) {
            if (account.getUsername().fullName.equals(tempaccount.getUsername().fullName)
                    && account.getPassword().value.equals(tempaccount.getPassword().value)) {
                logger.info("Credentials Accepted");
                MainApp.getUi().restart(account.getUsername().fullName);
                return new CommandResult(MESSAGE_SUCCESS);
            }
        }
        return new CommandResult(MESSAGE_FAILURE);

    }
}
