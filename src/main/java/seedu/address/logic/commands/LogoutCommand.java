//@@author cqhchan
package seedu.address.logic.commands;

import seedu.address.MainApp;
import seedu.address.ui.Ui;

/**
 * The LOgout Function
 **/
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_LOGOUT_FAILURE = "Logout Failed ...";
    public static final String MESSAGE_LOGOUT_ACKNOWLEDGEMENT = "Logout as requested ...";

    @Override
    public CommandResult execute() {
        Ui ui = MainApp.getUi();
        try {
            ui.restart("addressbook");
            return new CommandResult(MESSAGE_LOGOUT_ACKNOWLEDGEMENT);
        } catch (Exception e) {

            return new CommandResult(MESSAGE_LOGOUT_FAILURE);
        }

    }

}
