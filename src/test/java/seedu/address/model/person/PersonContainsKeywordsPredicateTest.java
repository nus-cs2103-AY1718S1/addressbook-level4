//@@author tbhbhbh
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(
                firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate = new PersonContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy = new PersonContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsKeywords_returnsTrue() {
        // Zero keywords, returns empty list
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // One name
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // One tag
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // One initial
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("a"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("aLIce"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed order keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("colleagues", "Alice", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("colleagues", "friends").build()));

        // Mixed order with mixed case keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("colLEAgUes", "ALiCe", "fRiENds"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("colleagues", "friends").build()));
    }

    @Test
    public void test_personDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Keyword: initial does not exist
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("c"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        //Keyword: invalid tag
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("-ta!@"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends").build()));

        // Keywords: matches name, but not tag
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol", "friends"));
        assertFalse(predicate.test(new PersonBuilder().withName("Carol").withTags("ilovecs2103t").build()));

        // Keywords: matches tag, but not name
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol", "friends"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends").build()));


    }
}
