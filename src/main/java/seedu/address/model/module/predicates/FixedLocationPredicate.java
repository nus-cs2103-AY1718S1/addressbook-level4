package seedu.address.model.module.predicates;

import java.util.function.Predicate;

import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;

/**
 * Tests that a {@code ReadOnlyLesson}'s {@code location} matches the given location.
 */
public class FixedLocationPredicate implements Predicate<ReadOnlyLesson> {
    private final Location locationToTest;

    public FixedLocationPredicate(Location location) {
        this.locationToTest = location;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        if (lesson.getLocation().equals(locationToTest)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FixedLocationPredicate // instanceof handles nulls
                && this.locationToTest.equals(((FixedLocationPredicate) other).locationToTest)); // state check
    }

}
