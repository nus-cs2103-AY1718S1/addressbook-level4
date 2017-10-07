package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Interception of two predicates that Test that a {@code ReadOnlyPerson} matches both predicates
 */
public class InterceptionPredicate implements Predicate<ReadOnlyPerson> {
    private final Predicate<? super ReadOnlyPerson> predicate1;
    private final Predicate<? super ReadOnlyPerson> predicate2;

    public InterceptionPredicate(Predicate<? super ReadOnlyPerson> predicate1, Predicate<ReadOnlyPerson> predicate2) {
        this.predicate1 = predicate1;
        this.predicate2 = predicate2;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return predicate1.test(person) && predicate2.test(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterceptionPredicate // instanceof handles nulls
                && ((this.predicate1.equals(((InterceptionPredicate) other).predicate1)
                        && this.predicate2.equals(((InterceptionPredicate) other).predicate2))
                    || (this.predicate1.equals(((InterceptionPredicate) other).predicate2)
                        && this.predicate2.equals(((InterceptionPredicate) other).predicate1))
                    )); // state check
    }
}
