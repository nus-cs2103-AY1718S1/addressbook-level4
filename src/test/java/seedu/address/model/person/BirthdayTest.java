package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthdays
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("9199")); // less than 8 numbers
        assertFalse(Birthday.isValidBirthday("birthday")); // non-numeric
        assertFalse(Birthday.isValidBirthday("0911a991")); // alphabets within digits
        assertFalse(Birthday.isValidBirthday("1203 1996")); // spaces within digits
        assertFalse(Birthday.isValidBirthday("15/09/1993")); // forward slash within digits
        assertFalse(Birthday.isValidBirthday("15.09.1993")); // fullstops within digits

        // valid birthdays
        assertTrue(Birthday.isValidBirthday(""));
        assertTrue(Birthday.isValidBirthday("12111999")); // exactly 8 digits
    }
}
