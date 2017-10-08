package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthdays
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("01-07-1990")); // uses '-' instead of '/'
        assertFalse(Birthday.isValidBirthday("32/01/1991")); // there is no 32th in a month
        assertFalse(Birthday.isValidBirthday("10/13/1992")); // there isn't a 13th month
        assertFalse(Birthday.isValidBirthday("1993/11/21")); // does not follow 'DD/MM/YYYY' format
        assertFalse(Birthday.isValidBirthday("09/1994/30")); // does not follow 'DD/MM/YYYY' format

        // valid birthdays
        assertTrue(Birthday.isValidBirthday("01/01/1990"));
        assertTrue(Birthday.isValidBirthday("13/05/1991"));
        assertTrue(Birthday.isValidBirthday("24/06/1992"));
        assertTrue(Birthday.isValidBirthday("17/02/1993"));
        assertTrue(Birthday.isValidBirthday("09/08/1994"));
    }
}
