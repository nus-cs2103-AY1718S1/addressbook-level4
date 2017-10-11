package seedu.address.model.person.predicates;

import java.util.HashSet;
import java.util.function.Predicate;

import seedu.address.model.person.Email;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Email} is unique in the given list.
 */
public class UniqueEmailPredicate implements Predicate<ReadOnlyPerson> {
    private final HashSet<Email> uniqueEmailSet;

    public UniqueEmailPredicate(HashSet<Email> emailSet) {
        this.uniqueEmailSet = emailSet;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (uniqueEmailSet.contains(person.getEmail())) {
            uniqueEmailSet.remove(person.getEmail());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEmailPredicate // instanceof handles nulls
                && this.uniqueEmailSet.equals(((UniqueEmailPredicate) other).uniqueEmailSet)); // state check
    }

}
