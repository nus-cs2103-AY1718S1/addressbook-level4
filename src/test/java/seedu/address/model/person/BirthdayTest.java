package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author tingtx
public class BirthdayTest {
    @Test
    public void isValidBirthday() {
        // invalid Birthday
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("bi-rt-hday")); // non-numeric
        assertFalse(Birthday.isValidBirthday("bi-10-1994")); // non-numeric
        assertFalse(Birthday.isValidBirthday("12-09-")); // no year
        assertFalse(Birthday.isValidBirthday("12- -1996")); // no month
        assertFalse(Birthday.isValidBirthday("-12-1996")); // no date
        assertFalse(Birthday.isValidBirthday("1-12-1996")); // date less than 2 digit
        assertFalse(Birthday.isValidBirthday("01-2-1996")); // month less than 2 digit
        assertFalse(Birthday.isValidBirthday("01-12-199")); // year less than 4 digit
        assertFalse(Birthday.isValidBirthday("01121996")); // missing '-'
        assertFalse(Birthday.isValidBirthday("12/12/1996")); // '/' invalid
        assertTrue(Birthday.isValidBirthday("29-02-1997")); //invalid date


        // valid Birthday
        assertTrue(Birthday.isValidBirthday("12-12-1994")); // exact dd-mm-yyyy format
        assertTrue(Birthday.isValidBirthday(""));
    }
}
