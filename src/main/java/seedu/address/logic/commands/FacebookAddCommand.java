package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.ResponseList;
import facebook4j.User;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SocialInfoMapping;
import seedu.address.model.person.Address;
import seedu.address.model.person.DisplayPhoto;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favorite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.social.SocialInfo;
import seedu.address.model.tag.Tag;
import seedu.address.ui.BrowserPanel;

//@@author alexfoodw
/**
 * Adds a facebook contact to the address book.
 */
public class FacebookAddCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "facebookadd";
    public static final String COMMAND_ALIAS = "fbadd";
    public static final String EXAMPLE_NAME = "Barack Obama";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a facebook user to the address book.\n"
            + "Alias: " + COMMAND_ALIAS + "\n"
            + "Parameters: FACEBOOK_USER_NAME\n"
            + "Example: " + COMMAND_WORD + " " + EXAMPLE_NAME;

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_FACEBOOK_ADD_SUCCESS = " has been imported from Facebook!";
    public static final String MESSAGE_FACEBOOK_ADD_INITIATED = "User not authenticated, log in to proceed.";
    public static final String MESSAGE_FACEBOOK_ADD_ERROR = "Error with Facebook API call.";
    public static final String MESSAGE_FACEBOOK_ADD_PERSON_ERROR = "Error with creating Person Object";

    private String userName;
    private String toAddName;
    private String toAddId;
    private Person toAdd;
    private boolean isAddAll;

    /**
     * Creates an FacebookAddCommand to add the specified Facebook contact
     * @param trimmedArgs
     */
    public FacebookAddCommand(String trimmedArgs) {
        userName = trimmedArgs;
    }

    /**
     * Creates an alternative FacebookAddCommand to add the specified Facebook contact initiated by facebook
     * add all friends command.
     */
    public FacebookAddCommand(boolean isAddAll) {
        this.isAddAll = isAddAll;
        toAddName = FacebookAddAllFriendsCommand.getCurrentUserName();
        toAddId = FacebookAddAllFriendsCommand.getCurrentUserId();
    }

    /**
     * Completes the Facebook Add command
     * @throws CommandException
     */
    public void completeAdd() throws CommandException {
        // if add is not called from the FacebookAddAllFriends Command
        if (!isAddAll) {
            Facebook facebookInstance = FacebookConnectCommand.getFacebookInstance();
            ResponseList<User> friendList = null;

            // fetch data from Facebook
            try {
                friendList = facebookInstance.searchUsers(userName);
            } catch (FacebookException e) {
                throw new CommandException(MESSAGE_FACEBOOK_ADD_ERROR);
            }
            User user = friendList.get(0);
            toAddName = user.getName();
            toAddId = user.getId();
        }

        // Assign data to Person object
        try {
            Set<SocialInfo> socialInfos = new HashSet<>();
            SocialInfo facebookInfo = null;
            facebookInfo = SocialInfoMapping.parseSocialInfo("facebook " + toAddId);
            socialInfos.add(facebookInfo);

            Set<Tag> tags = new HashSet<>();
            tags.add(new Tag("facebookFriend"));

            toAdd = new Person(new Name(toAddName), new Phone(), new Email(), new Address(),
                    new Favorite(false), new DisplayPhoto(null), tags, socialInfos);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_FACEBOOK_ADD_PERSON_ERROR);
        }

        addContactToAddressBook();
        EventsCenter.getInstance().post(new NewResultAvailableEvent(
                toAddName + MESSAGE_FACEBOOK_ADD_SUCCESS, false));
    }

    /**
     * Adds the facebook contact to addressbook
     * @throws CommandException
     */
    private void addContactToAddressBook() throws CommandException {
        // add to model and return
        try {
            requireNonNull(model);
            System.out.println(toAdd);
            model.addPerson(toAdd);
        } catch (DuplicatePersonException e) {
            new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        if (!FacebookConnectCommand.isAuthenticated()) {
            BrowserPanel.setProcessType(COMMAND_WORD);
            BrowserPanel.setTrimmedArgs(userName);

            FacebookConnectCommand newFacebookConnect = new FacebookConnectCommand();
            newFacebookConnect.execute();

            return new CommandResult(MESSAGE_FACEBOOK_ADD_INITIATED);
        } else {
            completeAdd();
            return new CommandResult(toAddName + MESSAGE_FACEBOOK_ADD_SUCCESS);
        }
    }

}
