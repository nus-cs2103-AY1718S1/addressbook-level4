package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author karrui
public class PersonContainsRecentPredicateTest {
    private final Person alice = new PersonBuilder().build();
    private final Person bob = new PersonBuilder().withName("Bob").build();

    @Test
    public void equals() {
        List<ReadOnlyPerson> firstPredicatePersonList = Collections.singletonList(alice);
        List<ReadOnlyPerson> secondPredicatePersonList = Arrays.asList(alice, bob);

        PersonContainsRecentPredicate firstPredicate = new PersonContainsRecentPredicate(firstPredicatePersonList);
        PersonContainsRecentPredicate secondPredicate = new PersonContainsRecentPredicate(secondPredicatePersonList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsRecentPredicate firstPredicateCopy = new PersonContainsRecentPredicate(firstPredicatePersonList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_returnsTrue() {
        PersonContainsRecentPredicate predicate = new PersonContainsRecentPredicate(Collections.singletonList(alice));
        assertTrue(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void test_returnsFalse() {
        PersonContainsRecentPredicate predicate = new PersonContainsRecentPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().build()));
    }
}
