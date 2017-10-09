package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author khooroko
public class DisplayPostalCodeTest {

    @Test
    public void isValidDisplayPostalCode() {
        // invalid displayPostalCodees
        assertFalse(DisplayPostalCode.isValidDisplayPostalCode("")); // empty string
        assertFalse(DisplayPostalCode.isValidDisplayPostalCode(" ")); // spaces only
        assertFalse(DisplayPostalCode.isValidDisplayPostalCode("123456")); // numbers only

        // valid displayPostalCodees
        assertTrue(DisplayPostalCode.isValidDisplayPostalCode("S000000"));
        assertTrue(DisplayPostalCode.isValidDisplayPostalCode("S999999"));
        assertTrue(DisplayPostalCode.isValidDisplayPostalCode("S123456"));
    }
}
