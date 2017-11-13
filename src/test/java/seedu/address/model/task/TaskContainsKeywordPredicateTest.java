package seedu.address.model.task;

//@@author Esilocke

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TaskBuilder;

public class TaskContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TaskContainsKeywordPredicate firstPredicate = new TaskContainsKeywordPredicate(firstPredicateKeywordList);
        TaskContainsKeywordPredicate secondPredicate = new TaskContainsKeywordPredicate(secondPredicateKeywordList);
        TaskContainsKeywordPredicate thirdPredicate = new TaskContainsKeywordPredicate(firstPredicateKeywordList,
                true, false, false, 0);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TaskContainsKeywordPredicate firstPredicateCopy = new TaskContainsKeywordPredicate(firstPredicateKeywordList,
                false, false, false, 0);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        TaskContainsKeywordPredicate predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").build()));

        // Multiple keywords
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("Buy", "Pencil"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").build()));

        // Only one matching keyword
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("Pencil", "Pen"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pen").build()));

        // Mixed-case keywords
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("buY", "pEnciL"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TaskContainsKeywordPredicate predicate = new TaskContainsKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy").build()));

        // Non-matching keyword
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("Pen"));
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").build()));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        TaskContainsKeywordPredicate predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("Buy 3 Pencil").build()));

        // Multiple keywords
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("Buy", "Pencil"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("Buy Pencil").build()));

        // Only one matching keyword
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("Pencil", "Pen"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("Buy Pen").build()));

        // Mixed-case keywords
        predicate = new TaskContainsKeywordPredicate(Arrays.asList("buY", "pEnciL"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("Buy Pencil").build()));
    }

    @Test
    public void priorityMatches() {
        // Priority level equal to required level
        TaskContainsKeywordPredicate predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                false, true, false, 3);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withPriority("3").build()));

        // Priority level greater than required level
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                false, true, false, 3);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withPriority("4").build()));

        // Priority level less than required level
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                false, true, false, 3);
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withPriority("1").build()));

        // Name matches, priority check not required, even though priority level does not match
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                false, false, false, 3);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withPriority("1").build()));

        // Priority matches, but name does not match
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Something"),
                false, true, false, 3);
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withPriority("3").build()));
    }

    @Test
    public void stateMatches() {
        // States are equivalent
        TaskContainsKeywordPredicate predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, false, true, 0);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withState(true).build()));

        // States are different
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, false, false, 0);
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withState(true).build()));

        // Name matches, state check not required, even though state does not match
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                false, false, false, 0);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withState(true).build()));

        // State matches, but name does not match
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Something"),
                true, false, true, 0);
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withState(true).build()));
    }


    @Test
    public void combinationTests() {
        // At most 1 invalid input per test case

        // Matches all
        TaskContainsKeywordPredicate predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, true, true, 3);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withDescription("Get 3 Pencil now")
                .withPriority("4").withState(true).build()));

        // Name does not match, but description does
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, true, true, 3);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Get something").withDescription("Get 3 Pencil now")
                .withPriority("4").withState(true).build()));

        // Description does not match, but name does
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, true, true, 3);
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Buy 3 Pencil").withDescription("Get something")
                .withPriority("4").withState(true).build()));

        // Name or description matches, but priority does not
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, true, true, 3);
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withDescription("Get 3 Pencils now")
                .withPriority("2").withState(true).build()));

        // Name or description matches, but state does not
        predicate = new TaskContainsKeywordPredicate(Collections.singletonList("Pencil"),
                true, true, true, 3);
        assertFalse(predicate.test(new TaskBuilder().withTaskName("Buy Pencil").withDescription("Get 3 Pencils now")
                .withPriority("4").withState(false).build()));
    }
}
