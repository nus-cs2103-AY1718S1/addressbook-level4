package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class AnyContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AnyContainsKeywordsPredicate firstPredicate = new AnyContainsKeywordsPredicate(firstPredicateKeywordList);
        AnyContainsKeywordsPredicate secondPredicate = new AnyContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AnyContainsKeywordsPredicate firstPredicateCopy = new AnyContainsKeywordsPredicate(
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
    public void test_anyContainsKeywords_returnsTrue() {
        // One keyword
        AnyContainsKeywordsPredicate predicate = new AnyContainsKeywordsPredicate(
                Collections.singletonList("12345678"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Multiple keywords
        predicate = new AnyContainsKeywordsPredicate(Arrays.asList("friends", "12345678"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").withTags("friends").build()));

        // Only one matching keyword
        predicate = new AnyContainsKeywordsPredicate(Arrays.asList("friends", "87654321"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").withTags("friends").build()));

        // Mixed-case keywords
        predicate = new AnyContainsKeywordsPredicate(Arrays.asList("FRiEndS", "AliCE"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("friends", "family").build()));

        // Partial keywords
        predicate = new AnyContainsKeywordsPredicate(Arrays.asList("1234"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_anyDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AnyContainsKeywordsPredicate predicate = new AnyContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Non-matching keyword
        predicate = new AnyContainsKeywordsPredicate(Arrays.asList("97732443"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Keywords do not match address, email, phone and name
        predicate = new AnyContainsKeywordsPredicate(Arrays.asList("Side", "Ave", "bob@email.com", "987654321", "Bob"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}

