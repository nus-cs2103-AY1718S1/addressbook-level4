package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        AddressContainsKeywordPredicate firstPredicate = new AddressContainsKeywordPredicate(firstPredicateKeyword);
        AddressContainsKeywordPredicate secondPredicate = new AddressContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordPredicate firstPredicateCopy = new AddressContainsKeywordPredicate(firstPredicateKeyword);
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
        AddressContainsKeywordPredicate predicate = new AddressContainsKeywordPredicate("Blk 101 Address");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 101 Address").build()));

        // sub-string
        predicate = new AddressContainsKeywordPredicate("101");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 101 Address").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordPredicate("bLk 101 addrESs");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 101 Address").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {

        AddressContainsKeywordPredicate predicate = new AddressContainsKeywordPredicate("Different Address");
        assertFalse(predicate.test(new PersonBuilder().withAddress("Blk 101 Address").build()));

        predicate = new AddressContainsKeywordPredicate("Blk 101 Address Ave 6");
        assertFalse(predicate.test(new PersonBuilder().withAddress("Blk 101 Address").build()));
    }
}
