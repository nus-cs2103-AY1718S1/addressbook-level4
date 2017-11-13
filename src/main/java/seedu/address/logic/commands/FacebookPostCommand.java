package seedu.address.logic.commands;

import facebook4j.Facebook;
import facebook4j.FacebookException;

import javafx.scene.web.WebEngine;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.BrowserPanel;

//@@author alexfoodw
/**
 * Posts a message to a personal Facebook account.
 */
public class FacebookPostCommand extends Command {
    public static final String COMMAND_WORD = "facebookpost";
    public static final String COMMAND_ALIAS = "fbpost";
    public static final String EXAMPLE_POST = "Hello World!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": post a message to Facebook account\n"
            + "Alias: " + COMMAND_ALIAS + "\n"
            + "Parameters: MESSAGE\n"
            + "Example: " + COMMAND_WORD + " " + EXAMPLE_POST;

    public static final String MESSAGE_FACEBOOK_POST_SUCCESS = "Posted to Facebook!";
    public static final String MESSAGE_FACEBOOK_POST_INITIATED = "User not authenticated, log in to proceed.";
    public static final String MESSAGE_FACEBOOK_POST_ERROR = "Error posting to Facebook";

    private static String currentPost;
    private static WebEngine webEngine;

    private String toPost;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public FacebookPostCommand(String message) {
        currentPost = message;
        toPost = message;
    }

    /**
     * Completes the post command
     * @throws CommandException
     */
    public static void completePost() throws CommandException {
        Facebook facebookInstance = FacebookConnectCommand.getFacebookInstance();
        try {
            facebookInstance.postStatusMessage(currentPost);
        } catch (FacebookException e) {
            // exception not handled because Facebook API still throws an exception even if success,
            // so exception is ignored for now
            e.printStackTrace();
            new CommandException(MESSAGE_FACEBOOK_POST_ERROR);
        }

        EventsCenter.getInstance().post(new NewResultAvailableEvent(MESSAGE_FACEBOOK_POST_SUCCESS
                + " (to " + FacebookConnectCommand.getAuthenticatedUsername() + "'s page.)", false));
        webEngine = FacebookConnectCommand.getWebEngine();
        webEngine.load(FacebookConnectCommand.getAuthenticatedUserPage());
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!FacebookConnectCommand.isAuthenticated()) {
            BrowserPanel.setProcessType(COMMAND_WORD);
            FacebookConnectCommand newFacebookConnect = new FacebookConnectCommand();
            newFacebookConnect.execute();
            return new CommandResult(MESSAGE_FACEBOOK_POST_INITIATED);
        } else {
            completePost();
            return new CommandResult(MESSAGE_FACEBOOK_POST_SUCCESS + " (to "
                    + FacebookConnectCommand.getAuthenticatedUsername() + "'s page.)");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FacebookPostCommand // instanceof handles nulls
                && toPost.equals(((FacebookPostCommand) other).toPost));
    }
}
//@@author
