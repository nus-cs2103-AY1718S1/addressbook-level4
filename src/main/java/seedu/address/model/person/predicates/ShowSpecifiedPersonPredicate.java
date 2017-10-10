package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;


/**
 * Tests that a {@code ReadOnlyPerson} matches the given person.
 */
public class ShowSpecifiedPersonPredicate implements Predicate<ReadOnlyPerson> {
    private final ReadOnlyPerson specifiedPerson;

    public ShowSpecifiedPersonPredicate(ReadOnlyPerson specifiedPerson) {
        this.specifiedPerson = specifiedPerson;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (person.equals(specifiedPerson)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowSpecifiedPersonPredicate // instanceof handles nulls
                && this.specifiedPerson.equals(((ShowSpecifiedPersonPredicate) other).specifiedPerson)); // state check
    }

}
