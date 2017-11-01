//@@author cqhchan
package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.Ui;

//@@author cqhchan
/**
 *
 */
public class LoginCommand extends Command {


    public static final String COMMAND_WORD = "login";

    private static String MESSAGE_LOGIN_ACKNOWLEDGEMENT;

    public static String MESSAGE_SUCCESS = "Login Successful";

    public static String MESSAGE_FAILURE = "Username or Password Incorrect";

    public static String MESSAGE_USAGE = COMMAND_WORD + ": Login to private Database. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD";

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public LoginCommand(String userName, String userPassword) {
        if (userName.equals("private") && userPassword.equals("password")) {
            MESSAGE_LOGIN_ACKNOWLEDGEMENT = MESSAGE_SUCCESS;
            Ui ui = MainApp.getUi();
            ui.restart(userName);
        } else {
            MESSAGE_LOGIN_ACKNOWLEDGEMENT = MESSAGE_FAILURE;
        }
    }

    @Override
    public CommandResult execute() {

        return new CommandResult(MESSAGE_LOGIN_ACKNOWLEDGEMENT);
    }
}
