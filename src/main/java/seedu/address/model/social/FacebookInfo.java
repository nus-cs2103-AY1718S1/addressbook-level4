package seedu.address.model.social;

/**
 * Social Info for a Facebook account
 */
public class FacebookInfo extends SocialInfo {
    private static final String SOCIAL_TYPE = "FACEBOOK";
    private static final String URL_PREFIX = "https://www.facebook.com/";

    public FacebookInfo(String username) {
        super(username);
    }

    @Override
    public String getSocialType() {
        return SOCIAL_TYPE;
    }

    @Override
    public String getSocialUrl() {
        return URL_PREFIX + getUsername();
    }
}
