package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author nicholaschuayunzhi
public class RemarkContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        RemarkContainsKeywordPredicate firstPredicate = new  RemarkContainsKeywordPredicate(firstPredicateKeyword);
        RemarkContainsKeywordPredicate secondPredicate = new  RemarkContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RemarkContainsKeywordPredicate firstPredicateCopy = new  RemarkContainsKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsKeywords_returnsTrue() {
        // One keyword
        RemarkContainsKeywordPredicate predicate = new  RemarkContainsKeywordPredicate("likes");
        assertTrue(predicate.test(new PersonBuilder().withRemark("hates work but likes food").build()));
        //ignore case
        assertTrue(predicate.test(new PersonBuilder().withRemark("Likes to swim").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {
        RemarkContainsKeywordPredicate predicate = new RemarkContainsKeywordPredicate("hates");
        assertFalse(predicate.test(new PersonBuilder().withRemark("likes food").build()));

        //super-string
        predicate = new RemarkContainsKeywordPredicate("hates");
        assertFalse(predicate.test(new PersonBuilder().withRemark("I hate this").build()));

        // sub-string
        predicate = new RemarkContainsKeywordPredicate("like");
        assertFalse(predicate.test(new PersonBuilder().withRemark("hates work but likes food").build()));
    }
}
