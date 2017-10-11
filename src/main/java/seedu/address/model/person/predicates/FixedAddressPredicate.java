package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Address;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code address} matches the given address.
 */
public class FixedAddressPredicate implements Predicate<ReadOnlyPerson> {
    private final Address fixedAddress;

    public FixedAddressPredicate(Address fixedAddress) {
        this.fixedAddress = fixedAddress;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (person.getAddress().equals(fixedAddress)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FixedAddressPredicate // instanceof handles nulls
                && this.fixedAddress.equals(((FixedAddressPredicate) other).fixedAddress)); // state check
    }

}
