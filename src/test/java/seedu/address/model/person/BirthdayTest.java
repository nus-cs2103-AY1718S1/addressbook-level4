package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author leonchowwenhao
public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthdays
        assertFalse(Birthday.isValidBirthday("32/01/1991")); // there is no 32th in a month
        assertFalse(Birthday.isValidBirthday("10/13/1992")); // there isn't a 13th month
        assertFalse(Birthday.isValidBirthday("0/1/1994")); // day cannot be 0
        assertFalse(Birthday.isValidBirthday("1/0/1995")); // month cannot be 0
        assertFalse(Birthday.isValidBirthday("31/4/1990")); // april does not have a 31st day
        assertFalse(Birthday.isValidBirthday("30/02/1996")); // february does not have a 30th day
        assertFalse(Birthday.isValidBirthday("29/02/1997")); // 1997 is not a leap year
        assertFalse(Birthday.isValidBirthday("1993/11/21")); // does not follow 'DD/MM/YYYY' format
        assertFalse(Birthday.isValidBirthday("09/1994/30")); // does not follow 'DD/MM/YYYY' format

        // valid birthdays
        assertTrue(Birthday.isValidBirthday("01/01/1990"));
        assertTrue(Birthday.isValidBirthday("13-05-1991"));
        assertTrue(Birthday.isValidBirthday("24.06.1992"));
        assertTrue(Birthday.isValidBirthday("17-02/1993"));
        assertTrue(Birthday.isValidBirthday("09/08.1994"));
        assertTrue(Birthday.isValidBirthday("09.08-1995"));
        assertTrue(Birthday.isValidBirthday("29-02-1996"));
        assertTrue(Birthday.isValidBirthday("28-02-1997"));
        assertTrue(Birthday.isValidBirthday("30-04-1998"));
        assertTrue(Birthday.isValidBirthday("31-05-1999"));
        assertTrue(Birthday.isValidBirthday("1-2-2000"));
        assertTrue(Birthday.isValidBirthday("9-10-2001"));
        assertTrue(Birthday.isValidBirthday("21-6-2002"));
    }
}
