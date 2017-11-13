//@@author tbhbhbh
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.SocialRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UserName;

/**
 * Accesses the person's social media profile on the browser.
 */

public class SocialCommand extends Command {
    public static final String COMMAND_WORD = "social";
    public static final String MESSAGE_SOCIAL_EMPTY = " does not have chosen social media: ";
    public static final String MESSAGE_SOCIAL_UNSUPPORTED = "Chosen social media not supported: ";
    public static final String MESSAGE_IF_MISSING = "(Check the person's username fields if his/her page does not "
            + "exist)";
    public static final String MESSAGE_INSTAGRAM = "'s Instagram profile\n";
    public static final String MESSAGE_TWITTER = "'s Twitter profile\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens up the target index's Twitter or Instagram "
            + "profile.\n"
            + "Parameters: INDEX CHOSEN_SOCIAL_MEDIA\n"
            + "CHOSEN_SOCIAL_MEDIA: ig or tw\n"
            + "Example: " + COMMAND_WORD + " 1 ig";

    public static final String INSTAGRAM_URL_PREFIX = "https://www.instagram.com/";
    public static final String TWITTER_URL_PREFIX = "https://www.twitter.com/";

    private final Index index;
    private final String socialMedia;

    private String messageSocialSuccess = "Successfully loaded %1$s";
    private String url;
    private UserName userName;
    private Name realName;

    public SocialCommand(Index index, String socialMedia) {
        this.index = index;
        this.socialMedia = socialMedia;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + index.getOneBased());
        }

        realName = lastShownList.get(index.getZeroBased()).getName();

        if (this.isInstagramName()) {
            userName = lastShownList.get(index.getZeroBased()).getInstagramName();
            url = INSTAGRAM_URL_PREFIX;
            messageSocialSuccess = messageSocialSuccess + MESSAGE_INSTAGRAM + MESSAGE_IF_MISSING;
        } else if (this.isTwitterName()) {
            userName = lastShownList.get(index.getZeroBased()).getTwitterName();
            url = TWITTER_URL_PREFIX;
            messageSocialSuccess = messageSocialSuccess + MESSAGE_TWITTER + MESSAGE_IF_MISSING;
        } else {
            throw new CommandException(MESSAGE_SOCIAL_UNSUPPORTED
                    + socialMedia);
        }

        if (url.equals(TWITTER_URL_PREFIX + userName) || url.equals(INSTAGRAM_URL_PREFIX + userName)) {
            throw new CommandException(realName + MESSAGE_SOCIAL_EMPTY + convertToActualName(socialMedia));
        }
        EventsCenter.getInstance().post(new SocialRequestEvent(userName, url));
        return new CommandResult(String.format(messageSocialSuccess, realName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SocialCommand // instanceof handles nulls
                && this.socialMedia.equals(((SocialCommand) other).socialMedia)
                && this.index.equals(((SocialCommand) other).index)); // state check
    }

    public String getMessageSocialSuccess() {
        return this.messageSocialSuccess;
    }

    public boolean isInstagramName() {
        return this.socialMedia.equals("ig");
    }

    public boolean isTwitterName() {
        return this.socialMedia.equals("tw");
    }

    public String convertToActualName(String socialMedia) {
        return (socialMedia.equals("tw") ? "Twitter" : "Instagram");
    }

}
