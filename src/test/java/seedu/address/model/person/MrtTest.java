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

        //MRT stations not yet added (Downtown Line)
        assertFalse(Mrt.isValidMrt("Bukit Panjang"));
        assertFalse(Mrt.isValidMrt("Beauty World"));
        assertFalse(Mrt.isValidMrt("King ALbert Park"));

        //Valid MRT stations
        assertTrue(Mrt.isValidMrt("Ang Mo Kio"));
        assertTrue(Mrt.isValidMrt("Bukit Gombak"));
        assertTrue(Mrt.isValidMrt("Kallang"));
    }
}
