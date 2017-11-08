//@@author Pujitha97
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class DateOfBirthTest {
    @Test
    public void isValidDateOfBirth() {
        // invalid dob
        assertFalse(DateOfBirth.isValidDateOfBirth("")); // empty string
        assertFalse(DateOfBirth.isValidDateOfBirth(" ")); // spaces only

        // valid dob
        assertTrue(DateOfBirth.isValidDateOfBirth("27 01 1997")); // alphabets only
        assertTrue(DateOfBirth.isValidDateOfBirth("27 01/1997")); // alphabets only
        assertTrue(DateOfBirth.isValidDateOfBirth("27-01.97")); // alphabets only
        assertTrue(DateOfBirth.isValidDateOfBirth("27 JaN 1997")); // alphabets only
        assertTrue(DateOfBirth.isValidDateOfBirth("27 January 97")); // alphabets only
    }
}

