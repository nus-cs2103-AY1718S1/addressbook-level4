package seedu.address.model.person.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.predicates.FixedPhonePredicate;
import seedu.address.testutil.PersonBuilder;

public class FixedPhonePredicateTest {

    @Test
    public void equals() {

        Phone firstPhone = null;
        Phone secondPhone = null;

        try {
            firstPhone = new Phone(VALID_PHONE_AMY);
            secondPhone = new Phone(VALID_PHONE_BOB);
        } catch (IllegalValueException e) {
            assert false : "The phone shouldn't be be invalid";
        }

        FixedPhonePredicate firstPredicate = new FixedPhonePredicate(firstPhone);
        FixedPhonePredicate secondPredicate = new FixedPhonePredicate(secondPhone);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FixedPhonePredicate firstPredicateCopy = new FixedPhonePredicate(firstPhone);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different phone -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isThePhoneGiven_returnsTrue() {

        ReadOnlyPerson person = new PersonBuilder().build();
        Phone phone = person.getPhone();
        FixedPhonePredicate predicate = new FixedPhonePredicate(phone);
        assertTrue(predicate.test(person));

    }

    @Test
    public void test_isThePhoneGiven_returnsFalse() {

        ReadOnlyPerson person = new PersonBuilder().build();
        FixedPhonePredicate predicate = new FixedPhonePredicate(ALICE.getPhone());
        assertTrue(predicate.test(person));
    }
}
