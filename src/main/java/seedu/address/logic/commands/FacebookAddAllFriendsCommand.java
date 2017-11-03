package seedu.address.logic.commands;

//@@author alexfoodw

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.BrowserPanel;

/**
 * Adds all available friends from a personal Facebook account.
 */
public class FacebookAddAllFriendsCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "facebookaddallfriends";
    public static final String COMMAND_ALIAS = "fbaddall";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds all available friends from a Facebook account\n"
            + "Alias: " + COMMAND_ALIAS + "\n";

    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_SUCCESS = "All Valid Friends added to Facebook!";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_INITIATED = "User not authenticated, "
            + "log in to proceed.";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        if (!FacebookConnectCommand.isAuthenticated()) {
            BrowserPanel.setProcessType(COMMAND_ALIAS);
            FacebookConnectCommand newFacebookConnect = new FacebookConnectCommand();
            newFacebookConnect.execute();
            return new CommandResult(MESSAGE_FACEBOOK_LINK_INITIATED);
        } else {
            completeLink();
            return new CommandResult(MESSAGE_FACEBOOK_LINK_SUCCESS + " (to " + user + "'s page.)");
        }
    }


    /*
            ResponseList<TaggableFriend> page1 = facebookInstance.getTaggableFriends();
            Paging<TaggableFriend> paging1 = page1.getPaging();
            System.out.println("Page 1: " + page1);
            System.out.println("Next: " + page1.getPaging().getNext());
            System.out.println("size: " + page1.size());
    */

}
