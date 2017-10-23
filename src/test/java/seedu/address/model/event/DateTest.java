package seedu.address.model.event;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTest {
    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("date")); // non-numeric
        assertFalse(Date.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDate("2001/2/11")); // not dd/mm/yyyy format

        // valid date
        assertTrue(Date.isValidDate("2099-11-12"));
        assertTrue(Date.isValidDate("2011-12-03"));
        assertTrue(Date.isValidDate("2012-04-12"));
    }
}
