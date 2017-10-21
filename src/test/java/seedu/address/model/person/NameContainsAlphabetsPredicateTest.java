package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsAlphabetsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateAlphabetList = Collections.singletonList("first");
        List<String> secondPredicateAlphabetList = Arrays.asList("first", "second");

        NameContainsAlphabetsPredicate firstPredicate = new NameContainsAlphabetsPredicate(firstPredicateAlphabetList);
        NameContainsAlphabetsPredicate secondPredicate =
                new NameContainsAlphabetsPredicate(secondPredicateAlphabetList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsAlphabetsPredicate firstPredicateCopy =
                new NameContainsAlphabetsPredicate(firstPredicateAlphabetList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testNameContainsAlphabetsReturnsTrue() {
        // One Alphabet
        NameContainsAlphabetsPredicate predicate = new NameContainsAlphabetsPredicate(Collections.singletonList("A"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Multiple Separated Alphabets
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("A", "B"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only One Matching Alphabet Sequence
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("Bob", "Car"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-Case Alphabets
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("bO", "aLIC"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Full Keywords
        predicate = new NameContainsAlphabetsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testNameDoesNotContainAlphabetsReturnsFalse() {
        // Zero Keywords
        NameContainsAlphabetsPredicate predicate = new NameContainsAlphabetsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching Alphabets in Sequence
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("Dob"));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsAlphabetsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
