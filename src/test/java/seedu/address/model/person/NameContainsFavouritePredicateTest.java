package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsFavouritePredicateTest {
    @Test
    public void equals() {
        NameContainsFavouritePredicate firstPredicate = new NameContainsFavouritePredicate();

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsFavouritePredicate firstPredicateCopy = new NameContainsFavouritePredicate();
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_personIsFavourited_returnsTrue() {
        NameContainsFavouritePredicate predicate = new NameContainsFavouritePredicate();
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withFavourite("true").build()));
    }

    @Test
    public void test_personIsNotFavourited_returnsFalse() {
        NameContainsFavouritePredicate predicate = new NameContainsFavouritePredicate();
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }
}
