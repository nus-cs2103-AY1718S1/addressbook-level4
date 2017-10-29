package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        //invalid birthdays
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("91")); // less than 3 numbers
        assertFalse(Birthday.isValidBirthday("phone")); // non-numeric
        assertFalse(Birthday.isValidBirthday("9011p041")); // alphabets within digits
        assertFalse(Birthday.isValidBirthday("9312 1534")); // spaces within digits

        // valid birthdays
        assertTrue(Birthday.isValidBirthday("02-03-1995")); // follow regex
    }
}
