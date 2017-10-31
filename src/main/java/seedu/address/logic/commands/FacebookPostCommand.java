package seedu.address.logic.commands;

import facebook4j.Facebook;
import facebook4j.FacebookException;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.BrowserPanel;

//@@author alexfoodw
/**
 * Posts a message to a personal Facebook account.
 */
public class FacebookPostCommand extends Command {
    public static final String COMMAND_WORD = "facebook post";
    public static final String COMMAND_ALIAS = "fbpost";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": post a message to Facebook account\n"
            + "Alias: " + COMMAND_ALIAS + "\n"
            + "Parameters: MESSAGE\n"
            + "Example: " + COMMAND_WORD + " hello world!";

    public static final String MESSAGE_FACEBOOK_POST_SUCCESS = "Posted to Facebook!";
    public static final String MESSAGE_FACEBOOK_POST_INITIATED = "User not authenticated, log in to proceed.";
    public static final String MESSAGE_FACEBOOK_POST_ERROR = "Error posting to Facebook";

    private static String user;
    private static String toPost;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public FacebookPostCommand(String message) {
        toPost = message;
    }

    /**
     * Completes the post command
     * @throws CommandException
     */
    public static void completePost() throws CommandException {
        Facebook facebookInstance = FacebookConnectCommand.getFacebookInstance();
        user = null;
        try {
            user = facebookInstance.getName();
            facebookInstance.postStatusMessage(toPost);
        } catch (FacebookException e) {
            // exception not handled because Facebook API still throws an exception even if success,
            // so exception is ignored for now
            e.printStackTrace();
        }

        EventsCenter.getInstance().post(new NewResultAvailableEvent(
                MESSAGE_FACEBOOK_POST_SUCCESS + " (to " + user + "'s page.)", false));
        BrowserPanel.setPost(false);
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!FacebookConnectCommand.isAuthenticated()) {
            FacebookConnectCommand newFacebookConnect = new FacebookConnectCommand();
            BrowserPanel.setPost(true);
            newFacebookConnect.execute();
            return new CommandResult(MESSAGE_FACEBOOK_POST_INITIATED);
        } else {
            completePost();
            return new CommandResult(MESSAGE_FACEBOOK_POST_SUCCESS + " (to " + user + "'s page.)");
        }
    }
}
