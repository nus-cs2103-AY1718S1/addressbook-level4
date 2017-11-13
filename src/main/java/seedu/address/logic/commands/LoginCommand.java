//@@author PhuaJunJie
package seedu.address.logic.commands;

import seedu.address.commons.GoogleAuthenticator;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.LoadLoginEvent;

/**
 * For loading the authentication/login page
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "lg";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Directs the user to google's login page for the authentication"
            + "process\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD;

    private final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

    @Override
    public CommandResult execute() {
        String authenticationUrl = googleAuthenticator.getAuthorizationUrl();
        EventsCenter.getInstance().post(new LoadLoginEvent(authenticationUrl));

        return new CommandResult(Messages.LOGIN_MESSAGE);
    }
}
