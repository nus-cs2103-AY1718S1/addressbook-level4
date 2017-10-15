package seedu.address.model.person;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid Birthdays
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("")); //blank

        // valid addresses
        assertTrue(Birthday.isValidBirthday("15/02/1992"));
        assertTrue(Birthday.isValidBirthday("15/02/1993"));
        assertTrue(Birthday.isValidBirthday("15/02/1994"));
    }
}
