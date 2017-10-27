package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        //assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testPhoneContainsKeywordsReturnsTrue() {
        // One phone number search
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("94571111"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("94571111").build()));

        // Multiple phone number searches
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("94571111", "94571112"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("94571111").build()));
    }

    @Test
    public void testPhoneDoesNotContainKeywordsReturnsFalse() {
        // Zero phone number search
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("94571111").build()));

        // Non-matching phone number
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("94571112"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("94571113").build()));

        // Keywords match name, email and address, but does not match phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12346")
                .withEmail("alice@email.com").withAddress("Street").build()));
    }
}