package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Paging;
import facebook4j.ResponseList;
import facebook4j.TaggableFriend;
import javafx.scene.web.WebEngine;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SocialInfoMapping;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favorite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.social.SocialInfo;
import seedu.address.model.tag.Tag;
import seedu.address.ui.BrowserPanel;

import static java.lang.Thread.sleep;

//@@author alexfoodw
/**
 * Adds all available friends from a personal Facebook account.
 */
public class FacebookAddAllFriendsCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "facebookaddallfriends";
    public static final String COMMAND_ALIAS = "fbaddall";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds all available friends from a Facebook account\n"
            + "Alias: " + COMMAND_ALIAS + "\n";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_ERROR = "Error with Facebook Tagable Friends API call.";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_URL_ERROR = "Error with User Photo URL";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_SUCCESS = "All Valid Friends added to Facebook!";
    public static final String MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_INITIATED = "User not authenticated, "
            + "log in to proceed.";

    private static Facebook facebookInstance;
    private static String currentUserID;
    private static String currentUserName;

    //TODO: add description
    public static String getCurrentUserID() {
        return currentUserID;
    }

    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void addAllFriends() throws CommandException {
        facebookInstance = FacebookConnectCommand.getFacebookInstance();
        Paging<TaggableFriend> startingPaging;
        try {
            ResponseList<TaggableFriend> startingList = facebookInstance.getTaggableFriends();
            startingPaging = startingList.getPaging();
            addAllFriendsLoop(startingList, startingPaging);
        } catch (FacebookException e) {
            throw new CommandException(MESSAGE_FACEBOOK_ADD_ALL_FRIENDS_ERROR);
        }

    }

    private static void addAllFriendsLoop(ResponseList<TaggableFriend> startingList,
                                          Paging<TaggableFriend> startingPaging) {
        ResponseList<TaggableFriend> currentList = startingList;
        Paging<TaggableFriend> currentPaging = startingPaging;

        TaggableFriend friend;
        for(int i = 0; i < currentList.size(); i++){
            friend = currentList.get(i);
            currentUserName = friend.getName();
            getUserID(friend);
        }

        //loop until all friends have been added
        /*while(currentList != null){
            // add all friends in current list
            TaggableFriend friend;
            String userId;
            for(int i = 0; i < currentList.size(); i++){
                friend = currentList.get(i);
                userId = getUserID(friend);
            }
        }*/

    }

    private static void getUserID(TaggableFriend friend) {
        // extract photo ID
        String photoURL = friend.getPicture().getURL().toString();
        Pattern p = Pattern.compile("_(.*?)\\_");
        Matcher m = p.matcher(photoURL);
        m.matches();
        m.find();
        String photoID = m.group(1);

        // initialise getting user ID
        WebEngine webEngine = FacebookConnectCommand.getWebEngine();
        webEngine.load(FacebookConnectCommand.FACEBOOK_DOMAIN + photoID);
        System.out.println("userid setup");
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
