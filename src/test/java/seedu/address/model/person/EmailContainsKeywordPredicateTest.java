package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        EmailContainsKeywordPredicate firstPredicate = new EmailContainsKeywordPredicate(firstPredicateKeyword);
        EmailContainsKeywordPredicate secondPredicate = new EmailContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordPredicate firstPredicateCopy = new EmailContainsKeywordPredicate(firstPredicateKeyword);
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
        EmailContainsKeywordPredicate predicate = new EmailContainsKeywordPredicate("email@address.com");
        assertTrue(predicate.test(new PersonBuilder().withEmail("email@address.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordPredicate("eMaiL@aDdress.cOM");
        assertTrue(predicate.test(new PersonBuilder().withEmail("email@address.com").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        EmailContainsKeywordPredicate predicate = new EmailContainsKeywordPredicate("address@email.com");
        assertFalse(predicate.test(new PersonBuilder().withEmail("email@address.com").build()));

        //space between email arguments (ie e/email address vs e/email e/address which will be 2 different predicates)
        predicate = new EmailContainsKeywordPredicate("address email");
        assertFalse(predicate.test(new PersonBuilder().withEmail("email@address.com").build()));

    }
}
