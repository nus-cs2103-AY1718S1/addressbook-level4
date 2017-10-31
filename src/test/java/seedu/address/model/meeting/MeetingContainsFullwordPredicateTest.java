package seedu.address.model.meeting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.MeetingBuilder;

public class MeetingContainsFullwordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MeetingContainsKeywordsPredicate firstPredicate =
                new MeetingContainsKeywordsPredicate(firstPredicateKeywordList);
        MeetingContainsKeywordsPredicate secondPredicate =
                new MeetingContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MeetingContainsKeywordsPredicate firstPredicateCopy =
                new MeetingContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        MeetingContainsKeywordsPredicate predicate =
                new MeetingContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new MeetingBuilder().withNameMeeting("Shopping Date").build()));

        // Multiple keywords
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new MeetingBuilder().withNameMeeting("Shopping Date").build()));

        // Only one matching keyword
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("Date", "Alice"));
        assertTrue(predicate.test(new MeetingBuilder().withNameMeeting("Date Study").build()));

        // Mixed-case keywords
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("AliCe", "DaTe"));
        assertTrue(predicate.test(new MeetingBuilder().withNameMeeting("Shopping Date").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MeetingContainsKeywordsPredicate predicate = new MeetingContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new MeetingBuilder().withNameMeeting("Shopping").build()));

        // Non-matching keyword
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("Melvin"));
        assertFalse(predicate.test(new MeetingBuilder().withNameMeeting("Shopping Date").build()));

        // Keywords match DateTime, Place, but does not match name
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("30-10-2018", "NUS"));
        assertFalse(predicate.test(new MeetingBuilder().withNameMeeting("Shopping").withPlace("NUS")
                .withDateTime("30-10-2018 15:00").build()));
    }
}
