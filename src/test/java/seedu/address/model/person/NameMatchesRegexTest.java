// @@author donjar

package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameMatchesRegexTest {

    @Test
    public void equals() {
        String firstRegex = "^asdf$";
        String secondRegex = "a+s";

        NameMatchesRegexPredicate firstPredicate = new NameMatchesRegexPredicate(firstRegex);
        NameMatchesRegexPredicate secondPredicate = new NameMatchesRegexPredicate(secondRegex);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameMatchesRegexPredicate firstPredicateCopy = new NameMatchesRegexPredicate(firstRegex);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords() {
        NameMatchesRegexPredicate predicate = new NameMatchesRegexPredicate("^Man[mn]a$");
        assertTrue(predicate.test(new PersonBuilder().withName("Manna").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Manma").build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Manma Chi").build()));
    }
}
