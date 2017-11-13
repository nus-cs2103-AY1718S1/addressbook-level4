package seedu.address.model.person;

//@@author wangyiming1019
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} are favoured.
 */
public class NameContainsFavouritePredicate implements Predicate<ReadOnlyPerson> {

    public NameContainsFavouritePredicate() {
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getFavourite();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsFavouritePredicate); // instanceof handles nulls
    }
}
