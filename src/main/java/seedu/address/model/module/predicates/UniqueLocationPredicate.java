package seedu.address.model.module.predicates;

import java.util.HashSet;
import java.util.function.Predicate;

import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Address} is unique in the given list.
 */
public class UniqueLocationPredicate implements Predicate<ReadOnlyLesson> {
    private final HashSet<Location> uniqueLocationSet;

    public UniqueLocationPredicate(HashSet<Location> locationSet) {
        this.uniqueLocationSet = locationSet;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        if (uniqueLocationSet.contains(lesson.getLocation())) {
            uniqueLocationSet.remove(lesson.getLocation());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLocationPredicate // instanceof handles nulls
                && this.uniqueLocationSet.equals(((UniqueLocationPredicate) other).uniqueLocationSet)); // state check
    }

}
