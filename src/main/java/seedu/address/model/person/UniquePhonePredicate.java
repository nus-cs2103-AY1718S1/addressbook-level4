package seedu.address.model.person;

import java.util.HashSet;
import java.util.function.Predicate;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone} is unique in the given list.
 */
public class UniquePhonePredicate implements Predicate<ReadOnlyPerson> {
    private final HashSet<Phone> uniquePhoneSet;

    public UniquePhonePredicate(HashSet<Phone> phoneSet) {
        this.uniquePhoneSet = phoneSet;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (uniquePhoneSet.contains(person.getPhone())) {
            uniquePhoneSet.remove(person.getPhone());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePhonePredicate // instanceof handles nulls
                && this.uniquePhoneSet.equals(((UniquePhonePredicate) other).uniquePhoneSet)); // state check
    }

}
