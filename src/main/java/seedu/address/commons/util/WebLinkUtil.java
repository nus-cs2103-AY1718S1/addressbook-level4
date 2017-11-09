package seedu.address.commons.util;

//@@author AngularJiaSheng

import java.util.HashMap;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 * Hashmap implementation
 */
public class WebLinkUtil {

    private HashMap<String, String> matchingWebsites = new HashMap<>();

    /*Regex that can be used to match website to certain categories denoted by the tag strings.*/
    private static final String INSTAGRAM_MATCH_REGEX = "(?i)^^.*(instagram.com|instagram|insta).*$";
    public static final String INSTAGRAM_TAG = "instagram";
    private static final String FACEBOOK_MATCH_REGEX = "(?i)^^.*(facebook.com|fb.com/|facebook).*$";
    public static final String FACEBOOK_TAG = "facebook";
    private static final String TWITTER_MATCH_REGEX = "(?i)^^.*(twitter.com|t.co|twitter).*$";
    public static final String TWITTER_TAG = "twitter";

    public WebLinkUtil() {
        matchingWebsites.put(FACEBOOK_MATCH_REGEX, FACEBOOK_TAG);
        matchingWebsites.put(INSTAGRAM_MATCH_REGEX, INSTAGRAM_TAG);
        matchingWebsites.put(TWITTER_MATCH_REGEX, TWITTER_TAG);
    }

    public HashMap<String, String> getMatchingWebsites() {
        return matchingWebsites;
    }
}
