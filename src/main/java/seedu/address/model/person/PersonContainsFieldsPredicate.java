package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code tags} matches all the predicates given.
 */
public class PersonContainsFieldsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<Predicate> predicates;

    public PersonContainsFieldsPredicate(List<Predicate> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        for (Predicate searchQuery : predicates) {
            if (!searchQuery.test(person)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsFieldsPredicate // instanceof handles nulls
                && checkEquality((PersonContainsFieldsPredicate) other)); // state check
    }

    /**
     * returns true if other predicate is functionally the same as this predicate
     * and have the same number of predicates
     */
    public boolean checkEquality(PersonContainsFieldsPredicate other) {
        List<Predicate> copyList = other.predicates.stream().collect(Collectors.toList());

        //TODO: Remove this nest
        for (Predicate p1: other.predicates) {
            for (Predicate p : predicates) {
                if (p.equals(p1)) {
                    copyList.remove(p1);
                    break;
                }
            }
        }
        return copyList.size() == 0;
    }
}
