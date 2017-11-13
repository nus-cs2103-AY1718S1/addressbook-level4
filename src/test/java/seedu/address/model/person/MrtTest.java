package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MrtTest {
    @Test
    public void isValidMrt() throws Exception {
        //blank mrt
        assertFalse(Mrt.isValidMrt("")); // empty string
        assertFalse(Mrt.isValidMrt(" ")); // spaces only

        //mispelled mrt
        assertFalse(Mrt.isValidMrt("Jurog East"));
        assertFalse(Mrt.isValidMrt("CityHall"));
        assertFalse(Mrt.isValidMrt("China Town"));

        //abbrevated versions
        assertFalse(Mrt.isValidMrt("Jurong"));
        assertFalse(Mrt.isValidMrt("Holland V"));
        assertFalse(Mrt.isValidMrt("CCK"));

        //Valid MRT stations
        assertTrue(Mrt.isValidMrt("Ang Mo Kio"));
        assertTrue(Mrt.isValidMrt("Bukit Gombak"));
        assertTrue(Mrt.isValidMrt("Yishun"));
    }
}
