package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EventVenueTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(EventVenue.isValidAddress("")); // empty string
        assertFalse(EventVenue.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(EventVenue.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(EventVenue.isValidAddress("-")); // one character
        assertTrue(EventVenue.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}

