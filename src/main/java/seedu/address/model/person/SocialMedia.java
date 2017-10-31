package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

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
     * Validates given usernames.
     *
     * @throws IllegalValueException if either of given username string is invalid.
     */
    public SocialMedia(String facebook, String twitter, String instagram) throws IllegalValueException {
        if (facebook == null) {
            facebook = "";
        }

        if (twitter == null) {
            twitter = "";
        }

        if (instagram == null) {
            instagram = "";
        }

        /*if(!isValidName(facebook) || !isValidName(twitter) || !isValidName(instagram)) {
            throw new IllegalValueException(MESSAGE_USERNAME_CONSTRAINTS);
        }*/

        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
    }

    public SocialMedia(SocialMedia oldData, SocialMedia newData) {
        facebook = newData.facebook.equals("") ? oldData.facebook : newData.facebook;
        twitter = newData.twitter.equals("") ? oldData.twitter : newData.twitter;
        instagram = newData.instagram.equals("") ? oldData.instagram : newData.instagram;
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
