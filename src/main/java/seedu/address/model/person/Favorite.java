package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Favorite status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFavoriteStatus(String)}
 */
public class Favorite {

    /**
     * Default favorite statuses
     */
    public enum FavoriteStatus {
        yes,
        no;
    }

    public static final String MESSAGE_FAVORITE_CONSTRAINTS =
            "Favorite status can only be either 'yes' or 'no'";

    public static final String FAVORITE_VALIDATION_REGEX = "yes|no";

    public final String value;

    /**
     * Validates given favorite status.
     *
     * @throws IllegalValueException if given favorite status string is invalid.
     */
    public Favorite(String favorite) throws IllegalValueException {
        requireNonNull(favorite);
        if (!isValidFavoriteStatus(favorite)) {
            throw new IllegalValueException(MESSAGE_FAVORITE_CONSTRAINTS);
        }
        this.value = favorite;
    }

    /**
     * Returns true if a given string is a valid favorite status.
     */
    public static boolean isValidFavoriteStatus(String test) {
        return test.matches(FAVORITE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
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
