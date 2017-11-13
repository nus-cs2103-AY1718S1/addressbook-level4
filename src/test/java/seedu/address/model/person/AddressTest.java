package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class AddressTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address

        // address not filled in
        assertTrue(Address.isValidAddress(Address.ADDRESS_TEMPORARY));
    }

    //@@author DarrenCzen
    @Test
    public void testSymmetricHashCode() throws IllegalValueException {
        // equals and hashCode check address field value
        Address addressX = new Address("Blk 456, Den Road, #01-355");
        Address addressY = new Address("Blk 456, Den Road, #01-355");
        assertTrue(addressX.equals(addressY) && addressY.equals(addressX));
        assertTrue(addressX.hashCode() == addressY.hashCode());
    }
}
