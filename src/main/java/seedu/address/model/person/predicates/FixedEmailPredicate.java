package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Email;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Email} matches the given email.
 */
public class FixedEmailPredicate implements Predicate<ReadOnlyPerson> {
    private final Email fixedEmail;

    public FixedEmailPredicate(Email fixedEmail) {
        this.fixedEmail = fixedEmail;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (person.getEmail().equals(fixedEmail)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FixedEmailPredicate // instanceof handles nulls
                && this.fixedEmail.equals(((FixedEmailPredicate) other).fixedEmail)); // state check
    }

}
