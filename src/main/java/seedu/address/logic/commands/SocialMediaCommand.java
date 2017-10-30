package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeBrowserPanelUrlEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Display social media page of the person identified using it's last displayed index from the address book.
 */
public class SocialMediaCommand extends Command {

    public static final String COMMAND_WORD = "socialmedia";
    public static final String COMMAND_ALIAS = "sm";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the social media of the person identified by the index number used in the last person listing.\n"
            + "Parameters: TYPE (either \"facebook\", \"twitter\", or\"instagram\")"
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "facebook 1";

    public static final String TYPE_FACEBOOK = "facebook";
    public static final String TYPE_TWITTER = "twitter";
    public static final String TYPE_INSTAGRAM = "instagram";

    public static final String URL_FACEBOOK = "https://www.facebook.com/";
    public static final String URL_TWITTER = "https://twitter.com/";
    public static final String URL_INSTAGRAM = "https://www.instagram.com/";

    public static final String MESSAGE_SUCCESS = "Social media shown!";
    public static final String MESSAGE_NO_FACEBOOK = "This person has no facebook.";
    public static final String MESSAGE_NO_TWITTER = "This person has no twitter.";
    public static final String MESSAGE_NO_INSTAGRAM = "This person has no instagram.";
    public static final String MESSAGE_INVALID_TYPE = "No such social media type.";

    private final Index targetIndex;
    private final String type;

    public SocialMediaCommand(Index targetIndex, String type) {
        this.targetIndex = targetIndex;
        this.type = type;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToEdit = lastShownList.get(targetIndex.getZeroBased());
        personToEdit.incrementAccess();

        switch(type) {
        case TYPE_FACEBOOK:
            if (personToEdit.getSocialMedia().facebook.equals("")) {
                throw new CommandException(MESSAGE_NO_FACEBOOK);
            } else {
                EventsCenter.getInstance().post(new ChangeBrowserPanelUrlEvent(
                        URL_FACEBOOK + personToEdit.getSocialMedia().facebook));
            }
            break;
        case TYPE_TWITTER:
            if (personToEdit.getSocialMedia().twitter.equals("")) {
                throw new CommandException(MESSAGE_NO_TWITTER);
            } else {
                EventsCenter.getInstance().post(new ChangeBrowserPanelUrlEvent(
                        URL_TWITTER + personToEdit.getSocialMedia().twitter));
            }
            break;
        case TYPE_INSTAGRAM:
            if (personToEdit.getSocialMedia().instagram.equals("")) {
                throw new CommandException(MESSAGE_NO_INSTAGRAM);
            } else {
                EventsCenter.getInstance().post(new ChangeBrowserPanelUrlEvent(
                        URL_INSTAGRAM + personToEdit.getSocialMedia().instagram));
            }
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_TYPE);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SocialMediaCommand)) {
            return false;
        }

        SocialMediaCommand e = (SocialMediaCommand) other;
        return targetIndex.equals(e.targetIndex) && type.equals(e.type);
    }
}
