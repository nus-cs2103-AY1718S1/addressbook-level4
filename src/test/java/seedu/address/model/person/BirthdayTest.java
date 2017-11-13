package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author nadhira15
public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthday
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("912345")); // less than 8 numbers
        assertFalse(Birthday.isValidBirthday("phone")); // non-numeric
        assertFalse(Birthday.isValidBirthday("9011p041")); // alphabets within digits
        assertFalse(Birthday.isValidBirthday("9011@041")); // characters within digits
        assertFalse(Birthday.isValidBirthday("9312 1534")); // spaces within digits
        assertFalse(Birthday.isValidBirthday("123456789123456789")); // more than 8 numbers

        // valid birthday
        assertTrue(Birthday.isValidBirthday("01/01/1991")); // exactly 8 numbers valid birthday
        assertTrue(Birthday.isValidBirthday("14/06/1996")); // exactly 8 numbers valid birthday
        assertTrue(Birthday.isValidBirthday("31/12/2001")); // exactly 8 numbers valid birthday
    }
}
