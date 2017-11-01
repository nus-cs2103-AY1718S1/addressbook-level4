package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TaskBuilder;

public class TaskHasKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TaskHasKeywordsPredicate firstPredicate = new TaskHasKeywordsPredicate(firstPredicateKeywordList);
        TaskHasKeywordsPredicate secondPredicate = new TaskHasKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TaskHasKeywordsPredicate firstPredicateCopy = new TaskHasKeywordsPredicate(firstPredicateKeywordList);
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
        TaskHasKeywordsPredicate predicate = new TaskHasKeywordsPredicate(Collections.singletonList("Fishing"));
        assertTrue(predicate.test(buildTask("Fishing Lake")));

        // Multiple keywords
        predicate = new TaskHasKeywordsPredicate(Arrays.asList("Fishing", "Lake"));
        assertTrue(predicate.test(buildTask("Fishing Lake")));

        // Only one matching keyword
        predicate = new TaskHasKeywordsPredicate(Arrays.asList("Skating", "Park"));
        assertTrue(predicate.test(buildTask("Ring Skating")));

        // Mixed-case keywords
        predicate = new TaskHasKeywordsPredicate(Arrays.asList("nUS", "PrOjeCT"));
        assertTrue(predicate.test(buildTask("NUS Project")));
    }

    @Test
    public void test_headerDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TaskHasKeywordsPredicate predicate = new TaskHasKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(buildTask("Sleep")));

        // Non-matching keyword
        predicate = new TaskHasKeywordsPredicate(Arrays.asList("Shopping"));
        assertFalse(predicate.test(buildTask("Cycling")));

    }

    /**
     * Returns Task object with only header
     */
    private static ReadOnlyTask buildTask(String header) {
        try {
            return new TaskBuilder().withHeader(header).withCompletionStatus().build();
        } catch (IllegalValueException ive) {
            assert false : "Not possible";
            return null;
        }
    }
}
