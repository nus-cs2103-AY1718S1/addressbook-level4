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
        assertFalse(Date.isValidDate("2131/12/33")); // not dd/mm/yyyy format

        // valid date
        assertTrue(Date.isValidDate("12/11/2019"));
        assertTrue(Date.isValidDate("11/10/2000"));
        assertTrue(Date.isValidDate("12/04/2018"));
    }
}
