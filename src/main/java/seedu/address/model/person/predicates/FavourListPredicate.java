package seedu.address.model.person.predicates;

import java.util.HashSet;
import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;


/**
 * Tests that if a {@code ReadOnlyPerson} if in the collection list.
 */
public class FavourListPredicate implements Predicate<ReadOnlyPerson> {
    private final HashSet<ReadOnlyPerson> collectionSet;

    public FavourListPredicate(HashSet<ReadOnlyPerson> collectionSet) {
        this.collectionSet = collectionSet;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return collectionSet.contains(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavourListPredicate // instanceof handles nulls
                && this.collectionSet.equals(((FavourListPredicate) other).collectionSet)); // state check
    }

}
