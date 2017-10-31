package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    //@@author chrisboo
    @Test
    public void isValidBirthday() {
        // invalid Birthdays
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday(""));  // blank
        assertFalse(Birthday.isValidBirthday("1 January 1999")); // valid date but invalid format
        assertFalse(Birthday.isValidBirthday("32/01/1999"));     // invalid date but valid format

        // valid Birthdays
        assertTrue(Birthday.isValidBirthday(null));
        assertTrue(Birthday.isValidBirthday("29/02/2000")); // test leap year
        assertTrue(Birthday.isValidBirthday("31/12/1993"));
        assertTrue(Birthday.isValidBirthday("01/01/1994"));
        assertTrue(Birthday.isValidBirthday("1/1/1994"));
    }
    //@@author
}
