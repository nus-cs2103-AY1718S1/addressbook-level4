package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import com.google.api.services.youtube.model.Channel;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.YouTubeAuthorizer;

//@@author jhchia7

/**
 * Represents a Person's Channel ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidChannelId(String)}
 */
public class ChannelId {

    public static final String MESSAGE_CHANNEL_ID_INVALID = "Person's Channel ID is invalid.";

    public final String value;

    /**
     * Validates given channel ID.
     *
     * @throws IllegalValueException if given channel ID string is invalid.
     */
    public ChannelId(String channelId) throws IllegalValueException {
        requireNonNull(channelId);
        if (!isValidChannelId(channelId)) {
            throw new IllegalValueException(MESSAGE_CHANNEL_ID_INVALID);
        }
        this.value = channelId;
    }

    /**
     * Returns true if a given string is a valid person channel ID.
     */
    public static boolean isValidChannelId (String test) {
        Channel channel = YouTubeAuthorizer.getYouTubeChannel(test, "statistics,snippet");
        boolean isChannelAvailable = (channel != null);
        return isChannelAvailable;

    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChannelId // instanceof handles nulls
                && this.value.equals(((ChannelId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
