package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
//@@author RonakLakhotia
public class DateOfBirthTest {

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(DateOfBirth.isValidBirthday("29.02.2001"));
        assertFalse(DateOfBirth.isValidBirthday("31.04.2000"));


        assertTrue(DateOfBirth.isValidBirthday("1.1.1997"));
        assertTrue(DateOfBirth.isValidBirthday("1-1-1997"));

    }
}
