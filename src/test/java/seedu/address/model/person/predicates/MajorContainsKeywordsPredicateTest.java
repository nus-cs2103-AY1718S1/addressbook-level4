//@@author heiseish
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class MajorContainsKeywordsPredicateTest {
    @Test
    public void test_majorContainsKeywords_returnsTrue() {
        // One keyword
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate(
                Collections.singletonList("Chemical"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Chemical Engineering").build()));

        // Multiple keywords
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("Chemical", "Engineering"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Chemical Engineering").build()));

        // Only one matching keyword
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("Chemical", "Engineering"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Engineering").build()));

        // Mixed-case keywords
        predicate = new MajorContainsKeywordsPredicate(Collections.singletonList("chemical"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Chemical Engineering").build()));
    }

    @Test
    public void test_majorDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMajor("chemical").build()));

        // Non-matching keyword
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("chemical", "engineering"));
        assertFalse(predicate.test(new PersonBuilder().withMajor("computer science").build()));

        // Keywords match name, email and birthday, address, but does not match major
        predicate = new MajorContainsKeywordsPredicate(
                Arrays.asList("Alice", "alice@email.com", "Main", "01/01/1990"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street")
                .withMajor("chemical").withBirthday("01/01/1990").build()));
    }

    @Test
    public void equals() throws Exception {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MajorContainsKeywordsPredicate firstPredicate =
                new MajorContainsKeywordsPredicate(firstPredicateKeywordList);
        MajorContainsKeywordsPredicate secondPredicate =
                new MajorContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MajorContainsKeywordsPredicate firstPredicateCopy =
                new MajorContainsKeywordsPredicate(firstPredicateKeywordList);
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
