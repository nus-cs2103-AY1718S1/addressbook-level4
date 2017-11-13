//@@author AceCentury

package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} is an exact match (case-insensitve).
 */
public class CaseInsensitiveExactNamePredicate implements Predicate<ReadOnlyPerson> {
    private final Name name;

    public CaseInsensitiveExactNamePredicate(Name name) {
        this.name = name;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (person.getName().toString().toLowerCase()
                .equals(name.toString().toLowerCase())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CaseInsensitiveExactNamePredicate // instanceof handles nulls
                && this.name.equals(((CaseInsensitiveExactNamePredicate) other).name)); // state check
    }
}
