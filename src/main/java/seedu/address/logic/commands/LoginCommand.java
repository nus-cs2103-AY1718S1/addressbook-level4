//@@author PhuaJunJie
package seedu.address.logic.commands;

import seedu.address.commons.GoogleAuthenticator;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.LoadLoginEvent;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
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
