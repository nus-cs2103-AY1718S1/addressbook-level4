package seedu.address.model.person;

import org.junit.Test;
import seedu.address.testutil.PersonBuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FuzzySearchPredicateTest {

    @Test
    public void equals() {
        FuzzySearchPredicate firstPredicate = new FuzzySearchPredicate("first keyword");
        FuzzySearchPredicate secondPredicate = new FuzzySearchPredicate("second keyword");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FuzzySearchPredicate firstPredicateCopy = new FuzzySearchPredicate("first keyword");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containedInPerson_returnsTrue() {
        FuzzySearchPredicate predicate;
        ReadOnlyPerson person = new PersonBuilder().build();

        predicate = new FuzzySearchPredicate(PersonBuilder.DEFAULT_NAME);
        assertTrue(predicate.test(person));

        predicate = new FuzzySearchPredicate(PersonBuilder.DEFAULT_PHONE.substring(2, 4));
        assertTrue(predicate.test(person));

        predicate = new FuzzySearchPredicate(PersonBuilder.DEFAULT_EMAIL.substring(2, 4));
        assertTrue(predicate.test(person));

        predicate = new FuzzySearchPredicate(PersonBuilder.DEFAULT_ADDRESS.substring(2, 4));
        assertTrue(predicate.test(person));

        predicate = new FuzzySearchPredicate(PersonBuilder.DEFAULT_TAGS.substring(1));
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_containedInPerson_returnsFalse() {
        FuzzySearchPredicate predicate;
        ReadOnlyPerson person = new PersonBuilder().build();

        predicate = new FuzzySearchPredicate("Bob");
        assertFalse(predicate.test(person));

        predicate = new FuzzySearchPredicate("family");
        assertFalse(predicate.test(person));

        predicate = new FuzzySearchPredicate("999");
        assertFalse(predicate.test(person));

        predicate = new FuzzySearchPredicate("@u.nus.edu");
        assertFalse(predicate.test(person));

        predicate = new FuzzySearchPredicate("Utown");
        assertFalse(predicate.test(person));
    }
}
