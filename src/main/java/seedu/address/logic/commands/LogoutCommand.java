package seedu.address.logic.commands;

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
        return new CommandResult(MESSAGE_LOGOUT_ACKNOWLEDGEMENT);
    }

}
