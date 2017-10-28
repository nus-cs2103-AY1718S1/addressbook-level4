package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;


public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("friends");
        List<String> secondPredicateKeywordList = Arrays.asList("neighbours");

        Predicate<ReadOnlyPerson> firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        Predicate<ReadOnlyPerson> secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        Predicate<ReadOnlyPerson> firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // exact match
        Predicate<ReadOnlyPerson> predicate = new TagContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friends", "teachers"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("frIEnds"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero email domain
        Predicate<ReadOnlyPerson> predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friEND"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Keywords match phone, name and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("12345", "Alice", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withTags("friends").withAddress("123, Main Street, SG 409999").build()));
    }
}
