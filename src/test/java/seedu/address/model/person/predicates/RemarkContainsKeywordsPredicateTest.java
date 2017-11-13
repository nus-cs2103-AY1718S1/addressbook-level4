//@@author heiseish
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class RemarkContainsKeywordsPredicateTest {
    @Test
    public void test_remarkContainsKeywords_returnsTrue() {
        // One keyword
        RemarkContainsKeywordsPredicate predicate = new RemarkContainsKeywordsPredicate(
                Collections.singletonList("dev"));
        assertTrue(predicate.test(new PersonBuilder().withRemark("dev").build()));

        // Multiple keywords
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("dev", "UI"));
        assertTrue(predicate.test(new PersonBuilder().withRemark("UI").build()));

        // Only one matching keyword
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("dev", "UI"));
        assertTrue(predicate.test(new PersonBuilder().withRemark("dev of UI").build()));

        // Mixed-case keywords
        predicate = new RemarkContainsKeywordsPredicate(Collections.singletonList("dev"));
        assertTrue(predicate.test(new PersonBuilder().withRemark("DEV").build()));
    }

    @Test
    public void test_remarkDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RemarkContainsKeywordsPredicate predicate = new RemarkContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRemark("dev").build()));

        // Non-matching keyword
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("dev", "UI"));
        assertFalse(predicate.test(new PersonBuilder().withRemark("Model").build()));

        // Keywords match name, email and birthday, address, but does not match Remark
        predicate = new RemarkContainsKeywordsPredicate(
                Arrays.asList("Alice", "alice@email.com", "Main", "01/01/1990"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Street")
                .withRemark("dev").withBirthday("01/01/1990").build()));
    }

    @Test
    public void equals() throws Exception {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RemarkContainsKeywordsPredicate firstPredicate =
                new RemarkContainsKeywordsPredicate(firstPredicateKeywordList);
        RemarkContainsKeywordsPredicate secondPredicate =
                new RemarkContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RemarkContainsKeywordsPredicate firstPredicateCopy =
                new RemarkContainsKeywordsPredicate(firstPredicateKeywordList);
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
