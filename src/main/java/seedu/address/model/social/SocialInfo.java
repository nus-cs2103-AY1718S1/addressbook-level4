package seedu.address.model.social;

import static java.util.Objects.requireNonNull;

/**
 * Represents information about a social media account in the address book.
 */
public abstract class SocialInfo {

    private final String username;

    public SocialInfo(String username) {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        this.username = trimmedUsername;
    }

    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the type (usually platform) of this social media information
     */
    public abstract String getSocialType();

    /**
     * Returns the link to the social media feed for the represented account
     */
    public abstract String getSocialUrl();

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "["
            + "Type: " + getSocialType() + ", "
            + "Username: " + getUsername() + ", "
            + "Link: " + getSocialUrl() + "]";
    }
}
