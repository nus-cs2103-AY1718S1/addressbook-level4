package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class DateOfBirthTest {
    @Test
    public void isValidDateOfBirth() {
        // invalid name
        assertFalse(DateOfBirth.isValidDateOfBirth("")); // empty string
        assertFalse(DateOfBirth.isValidDateOfBirth(" ")); // spaces only

        // valid name
        assertTrue(DateOfBirth.isValidDateOfBirth("27 01 1997")); // alphabets only
    }
}

