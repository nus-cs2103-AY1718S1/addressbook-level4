package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code address} matches the {@code fixedAddress}.
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
