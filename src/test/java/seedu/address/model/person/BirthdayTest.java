package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author mavistoh
public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        //invalid birthdays
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("91")); // less than 3 numbers
        assertFalse(Birthday.isValidBirthday("phone")); // non-numeric
        assertFalse(Birthday.isValidBirthday("9011p041")); // alphabets within digits
        assertFalse(Birthday.isValidBirthday("9312 1534")); // spaces within digits
        assertFalse(Birthday.isValidBirthday("29-02-1995")); // not a leap year
        assertFalse(Birthday.isValidBirthday("31-02-1995")); // feb cannot take 30/31
        assertFalse(Birthday.isValidBirthday("29-02-1995")); // not a leap year
        assertFalse(Birthday.isValidBirthday("31-09-1989")); // no 31st in sept
        assertFalse(Birthday.isValidBirthday("02.09-1989")); // separators not consistent

        // valid birthdays
        assertTrue(Birthday.isValidBirthday("-"));
        assertTrue(Birthday.isValidBirthday("02-03-1995")); // follow regex
        assertTrue(Birthday.isValidBirthday("02.03.1995")); // follow regex
        assertTrue(Birthday.isValidBirthday("02/03/1995")); // follow regex
    }
}
