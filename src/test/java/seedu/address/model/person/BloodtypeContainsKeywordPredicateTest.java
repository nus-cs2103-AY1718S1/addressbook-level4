package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author Ernest
public class BloodtypeContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("AB");
        List<String> secondPredicateKeywordList = Collections.singletonList("O-");

        BloodtypeContainsKeywordPredicate firstPredicate =
                new BloodtypeContainsKeywordPredicate(firstPredicateKeywordList);
        BloodtypeContainsKeywordPredicate secondPredicate =
                new BloodtypeContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BloodtypeContainsKeywordPredicate firstPredicateCopy =
                new BloodtypeContainsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotNull(firstPredicate);

        // different blood type -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testBloodtypeContainsKeywordsReturnsTrue() {
        // One keyword
        BloodtypeContainsKeywordPredicate predicate =
                new BloodtypeContainsKeywordPredicate(Collections.singletonList("AB"));
        assertTrue(predicate.test(new PersonBuilder().withBloodType("AB").build()));

        // Mixed-case keywords
        predicate = new BloodtypeContainsKeywordPredicate(Collections.singletonList("aB"));
        assertTrue(predicate.test(new PersonBuilder().withBloodType("AB").build()));
    }

    @Test
    public void testBloodtypeDoesNotContainKeywordReturnsFalse() {
        // No keyword
        BloodtypeContainsKeywordPredicate predicate = new BloodtypeContainsKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withBloodType("AB").build()));

        // Non-matching keyword
        predicate = new BloodtypeContainsKeywordPredicate(Collections.singletonList("O"));
        assertFalse(predicate.test(new PersonBuilder().withBloodType("AB").build()));
    }
}
