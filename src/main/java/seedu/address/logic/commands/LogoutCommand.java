package seedu.address.logic.commands;

import seedu.address.MainApp;
import seedu.address.ui.Ui;

/**
 * The LOgout Function
 **/
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_LOGOUT_ACKNOWLEDGEMENT = "Logout as requested ...";

    @Override
    public CommandResult execute() {
        Ui ui = MainApp.getUi();
        ui.restart("address");

        return new CommandResult(MESSAGE_LOGOUT_ACKNOWLEDGEMENT);
    }

}
