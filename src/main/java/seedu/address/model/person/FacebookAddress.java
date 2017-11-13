//@@author PokkaKiyo
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.net.URL;

/**
 * Represents a person's Facebook address
 * Guarantees: immutable; is always valid
 */
public class FacebookAddress {

    public static final String MESSAGE_FACEBOOKADDRESS_CONSTRAINTS =
            "Person Facebook address can take any values, can even be blank";

    public final String value;

    public FacebookAddress(String facebookAddress) {
        requireNonNull(facebookAddress);

        this.value = getInUrlFormIfNeeded(facebookAddress);
    }

    /**
     * If user's input is not in valid URL format, assume that the input is the facebook profile name of the contact,
     * and thus, append the necessary URL prefixes
     * @param facebookAddress
     * @return
     */
    private String getInUrlFormIfNeeded(String facebookAddress) {
        if (isValidUrl(facebookAddress)) {
            return facebookAddress;
        } else {
            return "https://www.facebook.com/" + facebookAddress;
        }
    }

    /**
     * Checks if a given string is in valid URL format
     * @param facebookAddress
     * @return
     */
    private boolean isValidUrl(String facebookAddress) {
        try {
            URL url = new URL(facebookAddress);
            url.toURI();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof FacebookAddress //instanceof handles nulls
                && this.value.equals(((FacebookAddress) other).value));

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
