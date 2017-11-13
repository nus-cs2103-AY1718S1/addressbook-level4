package seedu.address.model.person;

import java.util.function.Predicate;

//@@author taojiashu
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Favourite} is "True".
 */
public class IsFavouritePredicate implements Predicate<ReadOnlyPerson> {

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getFavourite().isFavourite();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsFavouritePredicate); // instanceof handles nulls
    }
}
