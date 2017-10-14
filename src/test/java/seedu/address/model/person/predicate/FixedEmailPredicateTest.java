package seedu.address.model.person.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.module.predicates.FixedEmailPredicate;
import seedu.address.testutil.PersonBuilder;

public class FixedEmailPredicateTest {

    @Test
    public void equals() {

        Email firstEmail = null;
        Email secondEmail = null;

        try {
            firstEmail = new Email(VALID_EMAIL_AMY);
            secondEmail = new Email(VALID_EMAIL_BOB);
        } catch (IllegalValueException e) {
            assert false : "The email shouldn't be invalid";
        }

        FixedEmailPredicate firstPredicate = new FixedEmailPredicate(firstEmail);
        FixedEmailPredicate secondPredicate = new FixedEmailPredicate(secondEmail);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FixedEmailPredicate firstPredicateCopy = new FixedEmailPredicate(firstEmail);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different email -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isTheEmailGiven_returnsTrue() {

        try {
            Email email = new Email("alice@gmail.com");
            FixedEmailPredicate predicate = new FixedEmailPredicate(email);
            assertTrue(predicate.test(new PersonBuilder().build()));
        } catch (IllegalValueException e) {
            assert false : "The email shouldn't be invalid";
        }

    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {

        Email email = null;

        try {
            email = new Email("alicee@gmail.com");
        } catch (IllegalValueException e) {
            assert false : "The email shouldn't be invalid";
        }

        FixedEmailPredicate predicate = new FixedEmailPredicate(email);
        assertFalse(predicate.test(new PersonBuilder().build()));
    }
}
