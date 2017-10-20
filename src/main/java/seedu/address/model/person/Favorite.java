package seedu.address.model.person;

/**
 * Represents a Favorite status in the address book.
 */
public class Favorite {

    public static final String MESSAGE_FAVORITE_CONSTRAINTS = "Only prefix is required.";
    private boolean value;

    /**
     * Allow only 'true' or 'false' values specified in AddCommandParser, EditCommandParser and test files.
     * If user specifies "f/"  : pass in 'true'
     * If user specifies "uf/" : pass in 'false'
     */
    public Favorite(boolean isFav) {
        this.value = isFav;
    }

    /**
     * Getter-method for returning favorite status
     */
    public boolean isFavorite() {
        return this.value;
    }

    /**
     * Formats 'true'/'false' values to "Yes"/"No" Strings to be displayed to user
     */
    @Override
    public String toString() {
        return isFavorite() ? "Yes" : "No";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favorite // instanceof handles nulls
                && this.value == (((Favorite) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(value);
    }

}
