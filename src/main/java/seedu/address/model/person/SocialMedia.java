package seedu.address.model.person;

//@@author kenpaxtonlim
/**
 * Represents a Person's social media usernames in the address book.
 */
public class SocialMedia {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Social media username should be alphanumeric without spaces";
    public static final String USERNAME_VALIDATION_REGEX = "[^\\s]+|[\\s*]";

    public final String facebook;
    public final String twitter;
    public final String instagram;

    /**
     * All usernames are empty.
     */
    public SocialMedia() {
        facebook = "";
        twitter = "";
        instagram = "";
    }

    /**
     * Set usernames based on input.
     */
    public SocialMedia(String facebook, String twitter, String instagram) {
        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
    }

    /**
     * Replace old usernames if new usernames is valid (not null).
     */
    public SocialMedia(SocialMedia oldData, SocialMedia newData) {
        facebook = newData.facebook == null ? oldData.facebook : newData.facebook;
        twitter = newData.twitter == null ? oldData.twitter : newData.twitter;
        instagram = newData.instagram == null ? oldData.instagram : newData.instagram;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        String toString = "";

        if (!facebook.equals("")) {
            toString += "FB: " + facebook + " ";
        }
        if (!twitter.equals("")) {
            toString += "TW: " + twitter + " ";
        }
        if (!instagram.equals("")) {
            toString += "IG: " + instagram;
        }
        if (toString.equals("")) {
            toString = "-No Social Media Accounts-";
        }
        return toString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SocialMedia // instanceof handles nulls
                && this.facebook.equals(((SocialMedia) other).facebook)
                && this.twitter.equals(((SocialMedia) other).twitter)
                && this.instagram.equals(((SocialMedia) other).instagram)); // state check
    }
}
