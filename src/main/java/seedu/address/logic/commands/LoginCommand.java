package seedu.address.logic.commands;

import com.sun.net.httpserver.Authenticator;
import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.model.AddressBook;
import seedu.address.ui.Ui;
import sun.rmi.runtime.Log;

import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class LoginCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final String COMMAND_WORD = "login";
    private static String MESSAGE_LOGIN_ACKNOWLEDGEMENT = "Login Successful";

    public LoginCommand(String userName, String userPassword){
        if (userName.equals("private") && userPassword.equals("password")){
            MESSAGE_LOGIN_ACKNOWLEDGEMENT = "Login Successful";


            Ui ui = MainApp.getUi();
            ui.restart(userName);


        }
        else {
            MESSAGE_LOGIN_ACKNOWLEDGEMENT = "Login Failed: " + "username " + userName +  " and password " +userPassword + " incorrect";
        }
    }

    @Override
    public CommandResult execute() {

        return new CommandResult(MESSAGE_LOGIN_ACKNOWLEDGEMENT);
    }
}
