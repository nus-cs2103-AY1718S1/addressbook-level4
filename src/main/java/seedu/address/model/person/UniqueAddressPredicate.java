package seedu.address.model.person;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Address} is unique in the given list.
 */
public class UniqueAddressPredicate implements Predicate<ReadOnlyPerson> {
    private final HashSet<Address> uniqueAddressSet;

    public UniqueAddressPredicate(HashSet<Address> AddressSet) {
        this.uniqueAddressSet = AddressSet;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (uniqueAddressSet.contains(person.getAddress())) {
            uniqueAddressSet.remove(person.getAddress());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAddressPredicate // instanceof handles nulls
                && this.uniqueAddressSet.equals(((UniqueAddressPredicate) other).uniqueAddressSet)); // state check
    }

}
