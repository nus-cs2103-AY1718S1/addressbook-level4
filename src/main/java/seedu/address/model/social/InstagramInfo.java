package seedu.address.model.social;

/**
 * Social Info for an Instagram account
 */
public class InstagramInfo extends SocialInfo {
    private static final String SOCIAL_TYPE = "INSTAGRAM";
    private static final String URL_PREFIX = "https://www.instagram.com/";

    public InstagramInfo(String username) {
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