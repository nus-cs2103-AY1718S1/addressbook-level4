//@@author hxy0229
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents whether a Person is favorite in the address book.
 * Guarantees: immutable; is valid as declared in }
 */
public class Favorite {

    public final boolean value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday address string is invalid.
     */
    public Favorite (boolean favorite) {
        requireNonNull(favorite);
        this.value = favorite;
    }

    /**
     * Returns if a given string is a valid person birthday.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favorite // instanceof handles nulls
                && this.value == (((Favorite) other).value)); // state check
    }

}

//@@author
