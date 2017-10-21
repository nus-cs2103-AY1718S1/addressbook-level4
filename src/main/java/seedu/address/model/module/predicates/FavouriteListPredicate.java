package seedu.address.model.module.predicates;

import java.util.HashSet;
import java.util.function.Predicate;

import seedu.address.model.module.ReadOnlyLesson;


/**
 * Tests that if a {@code ReadOnlyLesson} if in the favourite list.
 */
public class FavouriteListPredicate implements Predicate<ReadOnlyLesson> {
    private final HashSet<ReadOnlyLesson> favouriteSet;

    public FavouriteListPredicate(HashSet<ReadOnlyLesson> set) {
        this.favouriteSet = set;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        return favouriteSet.contains(lesson);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteListPredicate // instanceof handles nulls
                && this.favouriteSet.equals(((FavouriteListPredicate) other).favouriteSet)); // state check
    }

}
