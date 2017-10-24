package seedu.address.model.lesson;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.module.Location;

public class LocationTest {
    @Test
    public void isValidLocation() {
        // invalid addresses
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only

        // valid addresses
        assertTrue(Location.isValidLocation("COM2 02-03"));
        assertTrue(Location.isValidLocation("LT29")); // No space
        assertTrue(Location.isValidLocation("NUS SOC COM2 02-05")); // long address
    }
}
