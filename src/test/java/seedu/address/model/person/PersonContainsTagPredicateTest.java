//@@author tshradheya
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsTagPredicateTest {

    @Test
    public void equals() {

        String firstPredicateKeyword = "foo";
        String secondPredicateKeyword = "bar";

        PersonContainsTagPredicate firstPredicate = new PersonContainsTagPredicate(firstPredicateKeyword);
        PersonContainsTagPredicate secondPredicate = new PersonContainsTagPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsTagPredicate firstPredicateCopy = new PersonContainsTagPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsTag_returnsTrue() {

        // multiple people having 'friend' tag
        PersonContainsTagPredicate predicate = new PersonContainsTagPredicate("friend");
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));

        // one person having the tag
        predicate = new PersonContainsTagPredicate("enemy");
        assertTrue(predicate.test(new PersonBuilder().withTags("enemy").build()));

        // mixed-case tag
        predicate = new PersonContainsTagPredicate("coLLeagUe");
        assertTrue(predicate.test(new PersonBuilder().withTags("colleague").build()));

        // no one with the tag
        predicate = new PersonContainsTagPredicate("cs2103");
        assertTrue(predicate.test(new PersonBuilder().withTags("cs2103").build()));

    }

    @Test
    public void test_personDoesNotContainTag_returnsFalse() {

        // no keywords
        PersonContainsTagPredicate predicate = new PersonContainsTagPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withTags("enemy").build()));

        // not matching
        predicate = new PersonContainsTagPredicate("friend");
        assertFalse(predicate.test(new PersonBuilder().withTags("enemy").build()));

        // people associated with empty tag
        predicate = new PersonContainsTagPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));

        // keyword matches name but not tag
        predicate = new PersonContainsTagPredicate("Alice");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

    }
}
