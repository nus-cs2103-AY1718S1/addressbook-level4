//@@author heiseish
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class FacebookContainsKeywordsPredicateTest {
    @Test
    public void test_facebookContainsKeywords_returnsTrue() {
        // One keyword
        FacebookContainsKeywordsPredicate predicate = new FacebookContainsKeywordsPredicate(
                Collections.singletonList("zuck"));
        assertTrue(predicate.test(new PersonBuilder().withFacebook("zuck").build()));

        // Multiple keywords
        predicate = new FacebookContainsKeywordsPredicate(Arrays.asList("zuck", "galois"));
        assertTrue(predicate.test(new PersonBuilder().withFacebook("zuck").build()));

        // Only one matching keyword
        predicate = new FacebookContainsKeywordsPredicate(Arrays.asList("zuck", "galois"));
        assertTrue(predicate.test(new PersonBuilder().withFacebook("zuckerberg").build()));

        // Mixed-case keywords
        predicate = new FacebookContainsKeywordsPredicate(Collections.singletonList("zuck"));
        assertTrue(predicate.test(new PersonBuilder().withFacebook("ZUCK").build()));
    }

    @Test
    public void test_facebookDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FacebookContainsKeywordsPredicate predicate = new FacebookContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withFacebook("zuck").build()));

        // Non-matching keyword
        predicate = new FacebookContainsKeywordsPredicate(Arrays.asList("zuck", "galois"));
        assertFalse(predicate.test(new PersonBuilder().withFacebook("michaelJackson").build()));

        // Keywords match name, email and birthday, address, but does not match facebook
        predicate = new FacebookContainsKeywordsPredicate(
                Arrays.asList("Alice", "alice@email.com", "Main", "01/01/1990"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street")
                .withFacebook("galois").withBirthday("01/01/1990").build()));
    }

    @Test
    public void equals() throws Exception {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FacebookContainsKeywordsPredicate firstPredicate =
                new FacebookContainsKeywordsPredicate(firstPredicateKeywordList);
        FacebookContainsKeywordsPredicate secondPredicate =
                new FacebookContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FacebookContainsKeywordsPredicate firstPredicateCopy =
                new FacebookContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

}
//@@author
