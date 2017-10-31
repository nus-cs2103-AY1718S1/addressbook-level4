package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class ScheduleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("meeting");
        List<String> secondPredicateKeywordList = Arrays.asList("meeting");

        Predicate<ReadOnlyPerson> firstPredicate = new ScheduleContainsKeywordsPredicate(firstPredicateKeywordList);
        Predicate<ReadOnlyPerson> secondPredicate = new ScheduleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        Predicate<ReadOnlyPerson> firstPredicateCopy = new ScheduleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_scheduleContainsKeywords_returnsTrue() {
        // exact match
        Predicate<ReadOnlyPerson> predicate = new ScheduleContainsKeywordsPredicate(Collections.singletonList("Party"));
        assertTrue(predicate.test(new PersonBuilder().withSchedule("Party").build()));

        // Multiple tags
        predicate = new ScheduleContainsKeywordsPredicate(Arrays.asList("Team Meeting", "Party"));
        assertTrue(predicate.test(new PersonBuilder().withSchedule("Party").build()));

        // Mixed-case keywords
        predicate = new ScheduleContainsKeywordsPredicate(Arrays.asList("parTy"));
        assertTrue(predicate.test(new PersonBuilder().withSchedule("Party").build()));

    }

    @Test
    public void test_scheduleDoesNotContainKeywords_returnsFalse() {
        // Zero email domain
        Predicate<ReadOnlyPerson> predicate = new ScheduleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSchedule("Party").build()));

        // Non-matching keyword
        predicate = new ScheduleContainsKeywordsPredicate(Arrays.asList("Interview"));
        assertFalse(predicate.test(new PersonBuilder().withSchedule("Party").build()));

        // Keywords match phone, name and address, but does not match schedule
        predicate = new ScheduleContainsKeywordsPredicate(Arrays.asList("12345", "Alice", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withSchedule("Party")
                .withAddress("123, Main Street, SG 409999").build()));
    }
}
