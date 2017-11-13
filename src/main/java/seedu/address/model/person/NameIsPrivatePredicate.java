package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson} {@code Name} is private.
 */
public class NameIsPrivatePredicate implements Predicate<ReadOnlyPerson> {

    private final boolean isPrivate;

    public NameIsPrivatePredicate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (isPrivate) {
            return !person.isPrivate(); // returns list without private persons
        } else {
            return person.isPrivate(); // returns only private persons
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameIsPrivatePredicate); // instanceof handles nulls
    }
}
