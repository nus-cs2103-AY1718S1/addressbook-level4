package seedu.address.model.person.weblink;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Iterator;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.WebLinkUtil;



//@@author AngularJiaSheng
/**
 * Represents a WebLink in the address book.
 * only accept twitter, facebook and instagram links
 * WebLinkTag defines the category of this webLink.
 */
public class WebLink {

    private static final String MESSAGE_WEB_LINK_CONSTRAINTS = "please enter a valid weblink with http or https.";
    private static final String WEB_LINK_VALIDATION_REGEX = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]"
            + "*[-a-zA-Z0-9+&@#/%=~_|]";
    private static final String DEFAULT_TAG = "others";

    private  String webLinkInput;
    private  String webLinkTag;

    /**
     * Validates given web link.
     *
     * @throws IllegalValueException if the given webLink name string is invalid.
     */
    public WebLink(String name) throws IllegalValueException {

        requireNonNull(name);
        this.webLinkInput = name.trim();
        if (!isValidWebLink(webLinkInput)) {
            throw new IllegalValueException(MESSAGE_WEB_LINK_CONSTRAINTS);
        }
        this.webLinkTag = DEFAULT_TAG;

        HashMap<String, String> webLinkTagMap = new WebLinkUtil().getMatchingWebsites();
        Iterator<String> keySetIterator = webLinkTagMap.keySet().iterator();

        while (keySetIterator.hasNext()) {
            String webLinkMatchingRegex = keySetIterator.next();
            if (webLinkInput.matches(webLinkMatchingRegex)) {
                this.webLinkTag = webLinkTagMap.get(webLinkMatchingRegex);
                break;
            }
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
