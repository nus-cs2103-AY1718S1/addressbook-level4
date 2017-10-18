package seedu.address.model.person;

/**
 * Represents a Favorite status in the address book.
 */
public class Favorite {

    public static final String MESSAGE_FAVORITE_CONSTRAINTS = "Only prefix is required.";
    private Boolean value;

    /**
     * This allows user to supply just the prefix (/f) alone during Add and Edit Person.
     */
    public Favorite(Boolean isFav) throws NullPointerException {
        if (isFav == null) {
            throw new NullPointerException("favorite status cannot be empty");
        }
        this.value = isFav;
    }

    /*
     * Getter method
     */
    public boolean isFavorite() {
        return this.value;
    }

    @Override
    public String toString() {
        return isFavorite() ? "Yes" : "No";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favorite // instanceof handles nulls
                && this.value.equals(((Favorite) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
