package seedu.address.model.person.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.predicates.FixedAddressPredicate;
import seedu.address.testutil.PersonBuilder;

public class FixedAddressPredicateTest {

    @Test
    public void equals() {

        Address firstAddress = null;
        Address secondAddress = null;

        try {
            firstAddress = new Address(VALID_ADDRESS_AMY);
            secondAddress = new Address(VALID_ADDRESS_BOB);
        } catch (IllegalValueException e) {
            assert false : "The address shouldn't invalid";
        }

        FixedAddressPredicate firstPredicate = new FixedAddressPredicate(firstAddress);
        FixedAddressPredicate secondPredicate = new FixedAddressPredicate(secondAddress);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FixedAddressPredicate firstPredicateCopy = new FixedAddressPredicate(firstAddress);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different address -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isTheAddressGiven_returnsTrue() {

        Address address = null;

        try {
            address = new Address("123, Jurong West Ave 6, #08-111");
        } catch (IllegalValueException e) {
            assert false : "The address shouldn't invalid";
        }

        FixedAddressPredicate predicate = new FixedAddressPredicate(address);
        assertTrue(predicate.test(new PersonBuilder().build()));

    }

    @Test
    public void test_isTheAddressGiven_returnsFalse() {

        try {
            Address address = new Address("124, Jurong West Ave 6, #08-111");
            FixedAddressPredicate predicate = new FixedAddressPredicate(address);
            assertFalse(predicate.test(new PersonBuilder().build()));
        } catch (IllegalValueException e) {
            assert false : "The address shouldn't invalid";
        }
    }
}
