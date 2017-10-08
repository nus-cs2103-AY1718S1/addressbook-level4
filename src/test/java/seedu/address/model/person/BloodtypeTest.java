package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BloodtypeTest {

    @Test
    public void isValidBloodType() {
        // invalid addresses
        assertFalse(Bloodtype.isValidBloodType("")); // empty string
        assertFalse(Bloodtype.isValidBloodType(" ")); // spaces only
        assertFalse(Bloodtype.isValidBloodType("ABCDE")); // more than three characters

        // valid addresses
        assertTrue(Bloodtype.isValidBloodType("AB"));
        assertTrue(Bloodtype.isValidBloodType("O")); // one character
        assertTrue(Bloodtype.isValidBloodType("B+")); // inclusive of + or - symbol
    }
}
