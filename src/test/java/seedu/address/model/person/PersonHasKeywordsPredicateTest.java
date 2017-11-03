package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonHasKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonHasKeywordsPredicate firstPredicate = new PersonHasKeywordsPredicate(firstPredicateKeywordList, false);
        PersonHasKeywordsPredicate secondPredicate = new PersonHasKeywordsPredicate(secondPredicateKeywordList, false);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonHasKeywordsPredicate firstPredicateCopy =
                new PersonHasKeywordsPredicate(firstPredicateKeywordList, false);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonHasKeywordsPredicate predicate =
                new PersonHasKeywordsPredicate(Collections.singletonList("Alice"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonHasKeywordsPredicate(Arrays.asList("Alice", "Bob"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonHasKeywordsPredicate(Arrays.asList("Bob", "Carol"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonHasKeywordsPredicate(Arrays.asList("aLIce", "bOB"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PersonHasKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"), false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonHasKeywordsPredicate predicate = new PersonHasKeywordsPredicate(Collections.emptyList(), false);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonHasKeywordsPredicate(Arrays.asList("Carol"), false);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }
}
