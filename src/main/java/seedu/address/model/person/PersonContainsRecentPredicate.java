package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

//@@author karrui
/**
 * Tests that a {@code ReadOnlyPerson} matches any of the persons in the backing list.
 */
public class PersonContainsRecentPredicate implements Predicate<ReadOnlyPerson> {
    private final List<ReadOnlyPerson> recentPersons;

    public PersonContainsRecentPredicate(List<ReadOnlyPerson> recentPersons) {
        this.recentPersons = recentPersons;
    }

    /**
     * Tests that a {@code ReadOnlyPerson} matches any of the persons in the backing list.
     */
    @Override
    public boolean test(ReadOnlyPerson person) {
        return recentPersons.stream().anyMatch(p -> p.equals(person));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsRecentPredicate// instanceof handles nulls
                && this.recentPersons.equals(((PersonContainsRecentPredicate) other).recentPersons)); // state check
    }
}
