package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidPhone() {
        // invalid birthdays
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("91")); // less than 3 numbers
        assertFalse(Birthday.isValidBirthday("12011997")); // no forward slashes between digits
        assertFalse(Birthday.isValidBirthday("twelve/dec/nineteen-ninety")); // non-numeric
        assertFalse(Birthday.isValidBirthday("90/dec/1997")); // alphabets within digits
        assertFalse(Birthday.isValidBirthday("93/ 15/ 34")); // spaces within digits

        // valid birthday
        assertTrue(Birthday.isValidBirthday("20/11/1997")); // dd/mm/yyyy
        assertTrue(Birthday.isValidBirthday("20/11/97")); //dd/mm/yy
    }
}
