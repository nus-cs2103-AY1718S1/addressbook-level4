//@@author A0143832J
package seedu.address.model.person;

/**
 * Represents whether a Person is favorited in the address book.
 */
public class Favorite {
    public final boolean favorite;

    public Favorite() {
        this.favorite = false;
    }

    public Favorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getValue() {
        return this.favorite ? 1 : 0;
    }
    @Override
    public String toString() {
        return favorite ? "true" : "false";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favorite // instanceof handles nulls
                && this.favorite == (((Favorite) other).favorite)); // state check
    }
}
//@@author
