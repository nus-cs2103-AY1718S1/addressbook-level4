package seedu.address.model.person;

import java.util.HashSet;
import java.util.function.Predicate;

//@@author rushan-khor
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Bloodtype} matches any of the keywords given.
 */
public class HasPotentialDuplicatesPredicate implements Predicate<ReadOnlyPerson> {
    private final HashSet<Name> duplicateNames;

    public HasPotentialDuplicatesPredicate(HashSet<Name> duplicateNames) {
        this.duplicateNames = duplicateNames;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return duplicateNames.contains(person.getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HasPotentialDuplicatesPredicate // instanceof handles nulls
                && this.duplicateNames.equals(((HasPotentialDuplicatesPredicate) other).duplicateNames)); // state check
    }

}
