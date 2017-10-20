package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson} {@code Name} is private.
 */
public class PersonIsPinnedPredicate implements Predicate<ReadOnlyPerson> {

    public PersonIsPinnedPredicate() {
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.isPinned();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameIsPrivatePredicate); // instanceof handles nulls
    }

}
