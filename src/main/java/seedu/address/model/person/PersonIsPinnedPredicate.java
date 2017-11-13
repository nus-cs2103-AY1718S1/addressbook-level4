package seedu.address.model.person;

import java.util.function.Predicate;

//@@author Alim95
/**
 * Tests that a {@code ReadOnlyPerson} is pinned.
 */
public class PersonIsPinnedPredicate implements Predicate<ReadOnlyPerson> {

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.isPinned() && !person.isPrivate();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonIsPinnedPredicate); // instanceof handles nulls
    }
}
