//@@author majunting
package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests a {@code ReadOnlyPerson} is favorite
 */
public class FavoritePersonsPredicate implements Predicate<ReadOnlyPerson> {

    private final boolean favorite;

    public FavoritePersonsPredicate(String keyword) {
        this.favorite = "favorite".equals(keyword);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getFavorite().favorite == this.favorite;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavoritePersonsPredicate // instanceof handles nulls
                && this.favorite == ((FavoritePersonsPredicate) other).favorite); // state check
    }
}
