package seedu.address.model.lesson.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MA1101R;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Location;
import seedu.address.model.module.predicates.FixedLocationPredicate;
import seedu.address.testutil.LessonBuilder;

public class FixedLocationPredicateTest {

    @Test
    public void equals() {

        Location firstLocation = null;
        Location secondLocation = null;

        try {
            firstLocation = new Location(VALID_VENUE_MA1101R);
            secondLocation = new Location(VALID_VENUE_CS2101);
        } catch (IllegalValueException e) {
            assert false : "The location shouldn't invalid";
        }

        FixedLocationPredicate firstPredicate = new FixedLocationPredicate(firstLocation);
        FixedLocationPredicate secondPredicate = new FixedLocationPredicate(secondLocation);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FixedLocationPredicate firstPredicateCopy = new FixedLocationPredicate(firstLocation);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different address -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isTheAddressGiven_returnsTrue() {

        Location location = null;

        try {
            location = new Location("LT27");
        } catch (IllegalValueException e) {
            assert false : "The location shouldn't invalid";
        }

        FixedLocationPredicate predicate = new FixedLocationPredicate(location);
        assertTrue(predicate.test(new LessonBuilder().build()));

    }

    @Test
    public void test_isTheAddressGiven_returnsFalse() {

        try {
            Location location = new Location("LT1");
            FixedLocationPredicate predicate = new FixedLocationPredicate(location);
            assertFalse(predicate.test(new LessonBuilder().build()));
        } catch (IllegalValueException e) {
            assert false : "The location shouldn't invalid";
        }
    }
}
