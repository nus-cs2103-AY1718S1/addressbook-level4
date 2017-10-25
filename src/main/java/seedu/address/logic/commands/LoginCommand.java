package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.Ui;


/**
 *
 */
public class LoginCommand extends Command {


    public static final String COMMAND_WORD = "login";
    private static String MESSAGE_LOGIN_ACKNOWLEDGEMENT;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    public LoginCommand(String userName, String userPassword) {
        if (userName.equals("private") && userPassword.equals("password")) {
            MESSAGE_LOGIN_ACKNOWLEDGEMENT = "Login Successful";
            Ui ui = MainApp.getUi();
            ui.restart(userName);
        } else {
            MESSAGE_LOGIN_ACKNOWLEDGEMENT = "Login Failed: " + "username " + userName
                    + " and password " + userPassword + " incorrect";
        }
    }

    @Override
    public CommandResult execute() {

        return new CommandResult(MESSAGE_LOGIN_ACKNOWLEDGEMENT);
    }
}
