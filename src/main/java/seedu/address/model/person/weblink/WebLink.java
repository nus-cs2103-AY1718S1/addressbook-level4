package seedu.address.model.person.weblink;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.person.weblink.WebLinkUtil.FACEBOOK_MATCH_STRING;
import static seedu.address.model.person.weblink.WebLinkUtil.FACEBOOK_TAG;
import static seedu.address.model.person.weblink.WebLinkUtil.INSTAGRAM_MATCH_STRING;
import static seedu.address.model.person.weblink.WebLinkUtil.INSTAGRAM_TAG;
import static seedu.address.model.person.weblink.WebLinkUtil.LINKEDIN_MATCH_STRING;
import static seedu.address.model.person.weblink.WebLinkUtil.LINKEDIN_TAG;
import static seedu.address.model.person.weblink.WebLinkUtil.TWITTER_MATCH_STRING;
import static seedu.address.model.person.weblink.WebLinkUtil.TWITTER_TAG;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a WebLink in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidWebLink(String)}
 * only accept twitter, facebook, linkedin and instagram links
 * WebLinkTag defines the category of this webLink.
 */
public class WebLink {

    public static final String MESSAGE_WEB_LINK_CONSTRAINTS = "Hi, only valid facebook,"
            + " instagram, linkedin or twitter links will be accepted:)";
    public static final String WEB_LINK_VALIDATION_REGEX = "^^.*(|instagram.com|linkedin.com|twitter.com"
            + "|facebook.com).*$";

    public final String webLinkInput;
    public final String webLinkTag;

    /**
     * Validates given web link.
     *
     * @throws IllegalValueException if the given webLink name string is invalid.
     * TODO: better implementation in the future instead of hard code.
     */
    public WebLink(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedWebLink = name.trim();
        if (!isValidWebLink(trimmedWebLink)) {
            throw new IllegalValueException(MESSAGE_WEB_LINK_CONSTRAINTS);
        }
        this.webLinkInput = trimmedWebLink;

        if (webLinkInput.contains(FACEBOOK_MATCH_STRING)) {
            this.webLinkTag = FACEBOOK_TAG;
        } else if (webLinkInput.contains(TWITTER_MATCH_STRING)) {
            this.webLinkTag = TWITTER_TAG;
        } else if (webLinkInput.contains(LINKEDIN_MATCH_STRING)) {
            this.webLinkTag = LINKEDIN_TAG;
        } else if (webLinkInput.contains(INSTAGRAM_MATCH_STRING)) {
            this.webLinkTag = INSTAGRAM_TAG;
        } else {
            throw new IllegalValueException(MESSAGE_WEB_LINK_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidWebLink(String test) {
        return test.matches(WEB_LINK_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WebLink // instanceof handles nulls
                && this.webLinkInput.equals(((WebLink) other).webLinkInput)); // state check
    }

    @Override
    public int hashCode() {
        return webLinkInput.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + webLinkInput + ']';
    }

    /**
     * Format state as text for viewing.
     */
    public String toStringWebLink() {
        return webLinkInput;
    }

    /**
     * Format state as text for viewing.
     */
    public String toStringWebLinkTag() {
        return webLinkTag;
    }
}
