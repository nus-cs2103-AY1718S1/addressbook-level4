package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone} matches the given phone.
 */
public class FixedPhonePredicate implements Predicate<ReadOnlyPerson> {
    private final Phone fixedPhone;

    public FixedPhonePredicate(Phone fixedPhone) {
        this.fixedPhone = fixedPhone;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (person.getPhone().equals(fixedPhone)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FixedPhonePredicate // instanceof handles nulls
                && this.fixedPhone.equals(((FixedPhonePredicate) other).fixedPhone)); // state check
    }

}
