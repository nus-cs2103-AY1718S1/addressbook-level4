package seedu.address.model.meeting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PlaceTest {
    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(Place.isValidAddress("")); // empty string
        assertFalse(Place.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Place.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Place.isValidAddress("-")); // one character
        assertTrue(Place.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
