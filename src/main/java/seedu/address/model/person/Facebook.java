package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represent a person's facebook account in the contact list
 * It could be empty if the person doesn't have a facebook.
 * This property could help adding a picture of the target user to the contact list.
 * Form of wwww.facebook.com/{user-id} or just {user-id}
 */
public class Facebook {
    public final String value;

    public Facebook(String facebook) {
        requireNonNull(facebook);
        this.value = facebook.trim().replace("www.facebook.com/", "");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Facebook // instanceof handles nulls
                && this.value.equals(((Facebook) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
