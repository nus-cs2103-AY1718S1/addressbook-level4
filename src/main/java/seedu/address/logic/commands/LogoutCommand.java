package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LogoutAppRequestEvent;

//@@authot jelneo
/**
 * Handles logout of a user
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_LOGOUT_ACKNOWLEDGEMENT = "Logout successful";
    private static boolean isLoggedOut = false;

    public static void setLogoutStatus(boolean status) {
        isLoggedOut = status;
    }

    @Override
    public CommandResult execute() {
        model.logout();
        EventsCenter.getInstance().post(new LogoutAppRequestEvent(true));
        return new CommandResult(MESSAGE_LOGOUT_ACKNOWLEDGEMENT);
    }

}
