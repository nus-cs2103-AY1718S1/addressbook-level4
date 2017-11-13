package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

// @@author pwenzhe
/**
 * Represents a Person's favourite status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFav(String)}
 */
public class Favourite {

    public static final String MESSAGE_FAVOURITE_CONSTRAINTS =
            "Favourite is case insensitive and can only be 'yes', 'y', 'no', 'n' or empty";
    public static final String FAV_VALIDATION_REGEX = "yes|y|no|n";
    public final boolean isFavourite;
    public final String value;

    /**
     * Validates given favourite status.
     *
     * @throws IllegalValueException if given favourite status is invalid.
     */
    public Favourite(String favouriteArg) throws IllegalValueException {
        String formattedFav = favouriteArg.trim().toLowerCase();
        if (formattedFav.isEmpty()) {
            isFavourite = false;
            value = "no";
        } else {
            if (!isValidFav(formattedFav)) {
                throw new IllegalValueException(MESSAGE_FAVOURITE_CONSTRAINTS);
            }

            isFavourite = parseFav(formattedFav);
            value = favValue(formattedFav);
        }
    }

    /**
     * Returns true if a given string is a valid favourite status.
     */
    public static boolean isValidFav(String test) {
        return test.matches(FAV_VALIDATION_REGEX);
    }

    /**
     * Parses and returns the appropriate value for favourite status.
     */
    private boolean parseFav(String str) {
        if (str.matches("yes|y")) {
            return true;
        } else if (str.matches("no|n")) {
            return false;
        }

        return true;
    }

    /**
     * Formats favourite status into a string.
     */
    private String favValue(String str) {
        if (str.matches("yes|y")) {
            return "yes";
        } else if (str.matches("no|n")) {
            return "no";
        }

        return "";
    }

    /**
     * Returns if Person is a favourite.
     */
    public boolean getFavourite() {
        return isFavourite;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favourite // instanceof handles nulls
                && (this.isFavourite == ((Favourite) other).isFavourite)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
