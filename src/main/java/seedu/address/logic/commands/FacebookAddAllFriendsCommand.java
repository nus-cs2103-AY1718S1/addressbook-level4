package seedu.address.logic.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Paging;
import facebook4j.ResponseList;
import facebook4j.TaggableFriend;
import javafx.scene.web.WebEngine;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.BrowserPanel;


//@@author alexfoodw
/**
 * Adds all available friends from a personal Facebook account.
 */
//TODO: Alex - kaypoh page add all friends bug
//TODO: Alex - add all method descriptions

public class FacebookAddAllFriendsCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "facebookaddallfriends";
    public static final String COMMAND_ALIAS = "fbaddall";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds all available friends from a Facebook account\n"
            + "Alias: " + COMMAND_ALIAS + "\n";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_ERROR = "Error with Facebook Tagable Friends API call.";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_PAGING_ERROR = "Error with getting next page";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_SUCCESS = "All Valid Friends added to Facebook!";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_INITIATED = "User not authenticated, "
            + "log in to proceed.";

    private static Facebook facebookInstance;
    private static String currentUserID;
    private static String currentUserName;
    private static ResponseList<TaggableFriend> currentList;
    private static Paging<TaggableFriend> currentPaging;
    private static String currentPhotoID;
    private static int friendIndex = 0;

    public static String getCurrentPhotoID() {
        return currentPhotoID;
    }

    public static int getFriendIndex() {
        return friendIndex;
    }

    public static void setFriendIndex(int friendIndex) {
        FacebookAddAllFriendsCommand.friendIndex = friendIndex;
    }

    //TODO: add description
    public static String getCurrentUserID() {
        return currentUserID;
    }

    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void addAllFriends() throws CommandException {
        facebookInstance = FacebookConnectCommand.getFacebookInstance();
        try {
            currentList = facebookInstance.getTaggableFriends();
            currentPaging = currentList.getPaging();
            addNextFriend();
        } catch (FacebookException e) {
            throw new CommandException(MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_ERROR);
        }

    }

    public static void addNextFriend() throws CommandException {
        if (friendIndex >= currentList.size()) {
            // go to next list
            try {
                currentList = facebookInstance.fetchNext(currentPaging);
            } catch (FacebookException e) {
                throw new CommandException(MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_PAGING_ERROR);
            }
            if(currentList == null){
                EventsCenter.getInstance().post(new NewResultAvailableEvent(
                        MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_SUCCESS + " (From "
                                + FacebookConnectCommand.getAuthenticatedUsername() + "'s account)", false));
            }
            friendIndex = 0;
            currentPaging = currentList.getPaging();
        }
        TaggableFriend friend = currentList.get(friendIndex);
        currentUserName = friend.getName();
        // extract photo ID
        String photoURL = friend.getPicture().getURL().toString();
        Pattern p = Pattern.compile("_(.*?)\\_");
        Matcher m = p.matcher(photoURL);
        m.matches();
        m.find();
        currentPhotoID = m.group(1);

        // initialise getting user ID
        WebEngine webEngine = FacebookConnectCommand.getWebEngine();
        webEngine.load(FacebookConnectCommand.FACEBOOK_DOMAIN + currentPhotoID);

    }

    public static void setupNextFriend() {
        friendIndex++;
        try {
            addNextFriend();
        } catch (CommandException e) {
            new CommandException(MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_ERROR);
        }
    }

    public static void setUserID(String url){
        // extract photo ID
        Pattern p = Pattern.compile("set=a.(.*?)\\&type");
        Matcher m = p.matcher(url);
        m.matches();
        m.find();
        String groupID = m.group(1);
        String[] parts = groupID.split("\\.");
        currentUserID = parts[2];
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        if (!FacebookConnectCommand.isAuthenticated()) {
            BrowserPanel.setProcessType(COMMAND_WORD);
            FacebookConnectCommand newFacebookConnect = new FacebookConnectCommand();
            newFacebookConnect.execute();
            return new CommandResult(MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_INITIATED);
        } else {
            BrowserPanel.setProcessType(COMMAND_WORD);
            addAllFriends();
            return new CommandResult(MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_SUCCESS + " (From "
                    + FacebookConnectCommand.getAuthenticatedUsername() + "'s account)");
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
