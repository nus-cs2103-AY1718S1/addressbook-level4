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

public class FacebookAddAllFriendsCommand extends Command {
    public static final String COMMAND_WORD = "facebookaddallfriends";
    public static final String COMMAND_ALIAS = "fbaddall";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds all available friends from an authenticated Facebook account.\n"
            + "Alias: " + COMMAND_ALIAS + "\n";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_ERROR = "Error with Facebook Tagable Friends API call."
            + "User may not be registered as 'Test User'";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_PAGING_ERROR = "Error with getting next page";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_SUCCESS = " valid friends added from Facebook!";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_INITIATION_ERROR = "User not authenticated, "
            + "please input 'facebookconnect' command first.";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_INITIATED = "Adding all friends from Facebook...";
    public static final String EXTRACT_USER_ID_REGEX = "set=a.(.*?)\\&type";

    private static Facebook facebookInstance;
    private static String currentUserId;
    private static String currentUserName;
    private static ResponseList<TaggableFriend> currentList;
    private static Paging<TaggableFriend> currentPaging;
    private static String currentPhotoID;
    private static int totalFriendsAdded = 0;
    private static int friendIndex = 0;

    /**
     * Returns the current Facebook ID of the user being added
     * @return currentUserId
     */
    public static String getCurrentUserId() {
        return currentUserId;
    }

    /**
     * Returns the current Facebook Username of the user being added
     * @return currentUserName
     */
    public static String getCurrentUserName() {
        return currentUserName;
    }

    /**
     * Increments the counter of total friends added so far
     */
    public static void incrementTotalFriendsAdded() {
        totalFriendsAdded++;
    }

    /**
     * Adds all facebook contacts to addressbook
     * @throws CommandException
     */
    public static void addFirstFriend() throws CommandException {
        facebookInstance = FacebookConnectCommand.getFacebookInstance();
        try {
            currentList = facebookInstance.getTaggableFriends();
            currentPaging = currentList.getPaging();
            addNextFriend();
        } catch (FacebookException e) {
            throw new CommandException(MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_ERROR);
        }

    }

    /**
     * Proceeds to add the next available friend from facebook contacts to addressbook
     * @throws CommandException
     */
    public static void addNextFriend() throws CommandException {
        if (friendIndex >= currentList.size()) {
            // go to next list
            try {
                currentList = facebookInstance.fetchNext(currentPaging);
            } catch (FacebookException e) {
                throw new CommandException(MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_PAGING_ERROR);
            }
            if (currentList == null) {
                finishFacebookAddAllFriends();
                return;
            }
            friendIndex = 0;
            currentPaging = currentList.getPaging();
        }
        TaggableFriend friend = currentList.get(friendIndex);
        currentUserName = friend.getName();
        // extract photo ID
        String photoUrl = friend.getPicture().getURL().toString();
        Pattern p = Pattern.compile("_(.*?)\\_");
        Matcher m = p.matcher(photoUrl);
        m.matches();
        m.find();
        currentPhotoID = m.group(1);

        // initialise getting user ID
        WebEngine webEngine = FacebookConnectCommand.getWebEngine();
        webEngine.load(FacebookConnectCommand.FACEBOOK_DOMAIN + currentPhotoID);
    }

    /**
     * Sets up the counter and adds the next Facebook Contact
     */
    public static void setupNextFriend() {
        friendIndex++;
        try {
            addNextFriend();
        } catch (CommandException e) {
            new CommandException(MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_ERROR);
        }
    }

    /**
     * Extracts the user id for the required URL
     */
    public static void setUserId(String url) {
        // extract photo ID
        Pattern p = Pattern.compile(EXTRACT_USER_ID_REGEX);
        Matcher m = p.matcher(url);
        m.matches();
        m.find();
        String groupId = m.group(1);
        String[] parts = groupId.split("\\.");
        currentUserId = parts[2];
    }

    /**
     * Completes and exits the command
     */
    private static void finishFacebookAddAllFriends() {
        FacebookConnectCommand.loadUserPage();
        EventsCenter.getInstance().post(new NewResultAvailableEvent(totalFriendsAdded
                + MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_SUCCESS + " (From "
                + FacebookConnectCommand.getAuthenticatedUsername() + "'s account)", false));
        friendIndex = 0;
        totalFriendsAdded = 0;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!FacebookConnectCommand.isAuthenticated()) {
            throw new CommandException(MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_INITIATION_ERROR);
        } else {
            BrowserPanel.setProcessType(COMMAND_WORD);
            addFirstFriend();
            return new CommandResult(MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_INITIATED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FacebookAddAllFriendsCommand); // instanceof handles nulls
    }
}
//@@author
