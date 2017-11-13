package seedu.address.model.reminder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ReminderBuilder;

public class TaskContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TaskContainsKeywordsPredicate firstPredicate = new TaskContainsKeywordsPredicate(firstPredicateKeywordList);
        TaskContainsKeywordsPredicate secondPredicate = new TaskContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TaskContainsKeywordsPredicate firstPredicateCopy = new TaskContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_taskContainsKeywords_returnsTrue() {
        // One keyword
        TaskContainsKeywordsPredicate predicate = new TaskContainsKeywordsPredicate(
                Collections.singletonList("Birthday"));
        assertTrue(predicate.test(new ReminderBuilder().withTask("Birthday tomorrow").build()));

        // Multiple keywords
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("Birthday", "tomorrow"));
        assertTrue(predicate.test(new ReminderBuilder().withTask("Birthday tomorrow").build()));

        // Only one matching keyword
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("Birthday", "tomorrow"));
        assertTrue(predicate.test(new ReminderBuilder().withTask("Birthday yesterday").build()));

        // Mixed-case keywords
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("BIrThdAY", "TOmoRRoW"));
        assertTrue(predicate.test(new ReminderBuilder().withTask("Birthday Tomorrow").build()));
    }

    @Test
    public void test_taskDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TaskContainsKeywordsPredicate predicate = new TaskContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ReminderBuilder().withTask("Birthday").build()));

        // Non-matching keyword
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("Work"));
        assertFalse(predicate.test(new ReminderBuilder().withTask("Birthday Tomorrow").build()));

        // Keywords match priority, date and message, but does not match task name
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("Low", "02/02/2017", "16:00",
                "Buy", "present"));
        assertFalse(predicate.test(new ReminderBuilder().withTask("Birthday").withPriority("Low")
                .withDate("02/02/2017 16:00").withMessage("Buy present with others.").build()));
    }
}
