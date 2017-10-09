package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AddressTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only
        assertFalse(Address.isValidAddress("Blk 456, Den Road, #01-355")); // does not end with 6 digits
        assertFalse(Address.isValidAddress("Blk 456, Den Road, #01-355 345")); // ends with shorter than 6 digits

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355 345123"));
        assertTrue(Address.isValidAddress("- 435342")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA 123123")); // long address
    }
}
