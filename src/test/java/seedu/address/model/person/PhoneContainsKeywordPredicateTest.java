package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordPredicateTest {


    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        PhoneContainsKeywordPredicate firstPredicate = new  PhoneContainsKeywordPredicate(firstPredicateKeyword);
        PhoneContainsKeywordPredicate secondPredicate = new  PhoneContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordPredicate firstPredicateCopy = new  PhoneContainsKeywordPredicate(firstPredicateKeyword);
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
        PhoneContainsKeywordPredicate predicate = new  PhoneContainsKeywordPredicate("123456");
        assertTrue(predicate.test(new PersonBuilder().withPhone("123456").build()));

        // sub-string
        predicate = new PhoneContainsKeywordPredicate("234");
        assertTrue(predicate.test(new PersonBuilder().withPhone("123456").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {

        PhoneContainsKeywordPredicate predicate = new PhoneContainsKeywordPredicate("654321");
        assertFalse(predicate.test(new PersonBuilder().withPhone("123456").build()));

        predicate = new PhoneContainsKeywordPredicate("123456789");
        assertFalse(predicate.test(new PersonBuilder().withPhone("123456").build()));
    }
}
