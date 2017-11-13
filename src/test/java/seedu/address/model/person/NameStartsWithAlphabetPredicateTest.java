//@@author majunting
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameStartsWithAlphabetPredicateTest {

    @Test
    public  void equals() {
        String firstPredicateKeyword = "f";
        String secondPredicateKeyword = "s";

        NameStartsWithAlphabetPredicate firstPredicate = new NameStartsWithAlphabetPredicate(firstPredicateKeyword);
        NameStartsWithAlphabetPredicate secondPredicate = new NameStartsWithAlphabetPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameStartsWithAlphabetPredicate firstPredicateCopy = new NameStartsWithAlphabetPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameStartsWithKeyword_returnsTrue() {
        NameStartsWithAlphabetPredicate predicate = new NameStartsWithAlphabetPredicate("a");
        assertTrue(predicate.test(new PersonBuilder().withName("alice bob").build()));

        predicate = new NameStartsWithAlphabetPredicate("A");
        assertTrue(predicate.test(new PersonBuilder().withName("AliCE").build()));
    }

    @Test
    public void test_nameDoesNotStartWithKeyword_returnsFalse() {
        NameStartsWithAlphabetPredicate predicate = new NameStartsWithAlphabetPredicate(" ");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new NameStartsWithAlphabetPredicate("C");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new NameStartsWithAlphabetPredicate("a");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }
}
