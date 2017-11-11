package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.EventBuilder;

// @@author HouDenghao
public class EventNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EventNameContainsKeywordsPredicate firstPredicate =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);
        EventNameContainsKeywordsPredicate secondPredicate =
                new EventNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventNameContainsKeywordsPredicate firstPredicateCopy =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);

        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different events -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testNameContainsKeywordsReturnsTrue() {
        // One keyword
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("First"));

        assertTrue(predicate.test(new EventBuilder().withName("First Meeting").build()));

        // Multiple keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("First", "Meeting"));
        assertTrue(predicate.test(new EventBuilder().withName("First Meeting").build()));

        // Only one matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("First", "Meeting"));
        assertTrue(predicate.test(new EventBuilder().withName("Second Meeting").build()));

        // Mixed-case keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("firST", "mEeting"));
        assertTrue(predicate.test(new EventBuilder().withName("First Meeting").build()));
    }

    @Test
    public void testNameDoesNotContainKeywordsReturnsFalse() {
        // Zero keywords
        EventNameContainsKeywordsPredicate predicate = new EventNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withName("First").build()));

        // Non-matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("First"));
        assertFalse(predicate.test(new EventBuilder().withName("Second Meeting").build()));

        // Keywords match description and time but does not match name
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("coding", "01/01/2017"));
        assertFalse(predicate.test(
                new EventBuilder().withName("Meeting").withDescription("coding").withTime("01/01/2017").build()));
    }
}
