package seedu.address.logic.commands;

import facebook4j.Facebook;
import facebook4j.FacebookException;

import seedu.address.logic.commands.exceptions.CommandException;

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
    //public static final String MESSAGE_FACEBOOK_POST_ERROR = "Error posting to Facebook";

    private String toPost;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public FacebookPostCommand(String message) {
        toPost = message;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!FacebookConnectCommand.isAuthenticated()) {
            FacebookConnectCommand newFacebookConnect = new FacebookConnectCommand();
            newFacebookConnect.execute();
        }

        Facebook facebookInstance = FacebookConnectCommand.getFacebookInstance();
        String user = "";
        try {
            user = facebookInstance.getName();
            facebookInstance.postStatusMessage(toPost);
        } catch (FacebookException e) {
            e.printStackTrace();
        }

        return new CommandResult(MESSAGE_FACEBOOK_POST_SUCCESS + " (using " + user + "'s account.)");
    }
}
