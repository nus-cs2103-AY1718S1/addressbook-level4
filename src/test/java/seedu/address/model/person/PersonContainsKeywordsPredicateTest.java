package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FindPersonDescriptor;
import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {
    private final FindPersonDescriptor first = new FindPersonDescriptor();
    private final FindPersonDescriptor second = new FindPersonDescriptor();

    @Test
    public void equals() {
        second.setName("Ali Bob Charles");
        second.setPhone("12345678 35678965");
        second.setMrt("Serangoon Toa Payoh");

        PersonContainsKeywordsPredicate firstPredicateTrue =
                new PersonContainsKeywordsPredicate(true, first);
        PersonContainsKeywordsPredicate firstPredicateFalse =
                new PersonContainsKeywordsPredicate(false, first);
        PersonContainsKeywordsPredicate secondPredicateTrue =
                new PersonContainsKeywordsPredicate(true, second);
        PersonContainsKeywordsPredicate secondPredicateFalse =
                new PersonContainsKeywordsPredicate(false, second);

        // same object -> returns true
        assertTrue(firstPredicateTrue.equals(firstPredicateTrue));
        assertTrue(secondPredicateTrue.equals(secondPredicateTrue));

        // same values -> returns true
        PersonContainsKeywordsPredicate secondPredicateCopy =
                new PersonContainsKeywordsPredicate(true, second);
        assertTrue(secondPredicateTrue.equals(secondPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicateTrue.equals(1));

        //null -> returns false
        assertFalse(firstPredicateTrue.equals(null));

        // different person -> returns false
        assertFalse(firstPredicateTrue.equals(secondPredicateTrue));

        //same values but different types
        assertFalse(firstPredicateTrue.equals(firstPredicateFalse));
        assertFalse(secondPredicateTrue.equals(secondPredicateFalse));
    }

    @Test
    public void testPersonContainsKeywordsReturnsTrue() {
        second.setName("Ali Bob Charles");
        second.setPhone("12345678 35678965");
        second.setMrt("Serangoon Toa Payoh");

        // AND search all fields match at least 1 keyword in that field
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(true, second);
        assertTrue(predicate.test(
                new PersonBuilder().withName("Ali").withMrt("Serangoon").withPhone("12345678").build()));

        // OR search, only phone matches
        predicate = new PersonContainsKeywordsPredicate(false, second);
        assertTrue(predicate.test(
                new PersonBuilder().withName("Cindy").withPhone("12345678").withMrt("Bedok").build()));

        // Empty AND search
        predicate = new PersonContainsKeywordsPredicate(true, first);
        assertTrue(predicate.test(new PersonBuilder().withPhone("94571111").build()));
    }

    @Test
    public void testPersonDoesNotContainKeywordsReturnsFalse() {
        second.setName("Ali Bob Charles");
        second.setPhone("12345678 35678965");
        second.setMrt("Serangoon Toa Payoh");

        // Empty OR search
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(false, first);
        assertFalse(predicate.test(new PersonBuilder().withPhone("94571111").build()));

        // Non-matching OR search
        predicate = new PersonContainsKeywordsPredicate(false, second);
        assertFalse(predicate.test(
                new PersonBuilder().withName("Keagen").withPhone("94571113").withMrt("Redhill").build()));

        // AND search, Keywords match name, email and address, but does not match phone
        predicate = new PersonContainsKeywordsPredicate(true, second);
        assertFalse(predicate.test(new PersonBuilder().withName("Ali").withPhone("12346")
                .withMrt("Toa Payoh").build()));
    }
}
