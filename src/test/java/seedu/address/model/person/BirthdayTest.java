package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {
    @Test
    public void isValidEmail() {
        assertFalse(Birthday.isValidBirthday("")); // empty string
        // spaces only
        assertFalse(Birthday.isValidBirthday(" "));
        // missing parts
        // missing local part
        assertFalse(Birthday.isValidBirthday("11/11"));
        // missing '@' symbol
        assertFalse(Birthday.isValidBirthday("11"));
        // missing domain name
        assertFalse(Birthday.isValidBirthday("19/10/"));

        // invalid parts
        assertFalse(Birthday.isValidBirthday("-11/23/2332")); // using illegal characters
        assertFalse(Birthday.isValidBirthday("22/12/12222")); // too many numbers in the year part
        assertFalse(Birthday.isValidBirthday("q/qw/qweq")); // using letters instead of numbers
        assertFalse(Birthday.isValidBirthday("000/99/2323")); // too many numbers for the day
        assertFalse(Birthday.isValidBirthday("23 /12/1122")); // space between

        // valid email
        assertTrue(Birthday.isValidBirthday("11/12/1099"));
        assertTrue(Birthday.isValidBirthday("09/03/2010"));
        assertTrue(Birthday.isValidBirthday("12/13/1009")); // also accepts months which don't exists
        assertTrue(Birthday.isValidBirthday("00/00/2011"));
    }
}
