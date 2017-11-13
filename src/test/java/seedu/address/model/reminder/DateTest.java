//@@author duyson98

package seedu.address.model.reminder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTest {

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("0abc/01/1998 12:30")); // contains non-numeric characters
        assertFalse(Date.isValidDate("1998/01/01 12:30")); // wrong format
        assertFalse(Date.isValidDate("1/1/2017 6:30")); // without zeroes
        assertFalse(Date.isValidDate("32/01/2017 12:30")); // invalid day
        assertFalse(Date.isValidDate("01/13/2017 12:30")); // invalid month
        assertFalse(Date.isValidDate("01/01/20170 12:30")); // invalid year
        assertFalse(Date.isValidDate("01/01/2017 25:30")); // invalid hour
        assertFalse(Date.isValidDate("01/01/2017 12:60")); // invalid minute

        // valid date
        assertTrue(Date.isValidDate("10/08/2017 20:30"));
        assertTrue(Date.isValidDate("    01/01/2017    12:30  ")); // trailing space
    }
}
