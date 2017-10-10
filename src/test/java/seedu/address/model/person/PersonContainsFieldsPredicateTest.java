package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;

import org.junit.Test;

public class PersonContainsFieldsPredicateTest {

    @Test
    public void equals() {
        PersonContainsFieldsPredicate firstPredicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new NameContainsKeywordPredicate("name"),
                        new AddressContainsKeywordPredicate("address"),
                        new PhoneContainsKeywordPredicate("123")));

        PersonContainsFieldsPredicate secondPredicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new PhoneContainsKeywordPredicate("123"),
                        new AddressContainsKeywordPredicate("address"),
                        new NameContainsKeywordPredicate("name")));

        PersonContainsFieldsPredicate thirdPredicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new PhoneContainsKeywordPredicate("234"),
                        new AddressContainsKeywordPredicate("address"),
                        new NameContainsKeywordPredicate("name")));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different filter -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        //One keyword
        PersonContainsFieldsPredicate predicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new NameContainsKeywordPredicate(ALICE.getName().fullName)));

        assertTrue(predicate.test(ALICE));

        //Multiple keywords
        predicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new NameContainsKeywordPredicate(ALICE.getName().fullName),
                        new AddressContainsKeywordPredicate(ALICE.getAddress().value),
                        new EmailContainsKeywordPredicate(ALICE.getEmail().value)));

        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {

        PersonContainsFieldsPredicate predicate =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new NameContainsKeywordPredicate(ALICE.getName().fullName + " Alice")));

        assertFalse(predicate.test(ALICE));

        //one wrong predicate
        predicate  =
                new PersonContainsFieldsPredicate(Arrays.asList(
                        new NameContainsKeywordPredicate(ALICE.getName().fullName),
                        new AddressContainsKeywordPredicate(ALICE.getAddress().value),
                        new EmailContainsKeywordPredicate(BOB.getEmail().value)));
        assertFalse(predicate.test(ALICE));
    }

}
