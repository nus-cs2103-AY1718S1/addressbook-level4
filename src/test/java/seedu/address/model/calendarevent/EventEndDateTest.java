package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author yilun-zhu
public class EventEndDateTest {

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(EventEndDate.isValidDate("")); // empty string
        assertFalse(EventEndDate.isValidDate(" ")); // spaces only
        assertFalse(EventEndDate.isValidDate("20170809")); // no dash in between
        assertFalse(EventEndDate.isValidDate("phone")); // non-numeric
        assertFalse(EventEndDate.isValidDate("9011-p0-41")); // alphabets within digits
        assertFalse(EventEndDate.isValidDate("9312 1534")); // spaces within digits
        assertFalse(EventEndDate.isValidDate("2017-13-20")); // invalid month
        assertFalse(EventEndDate.isValidDate("2017-12-32")); // invalid day

        // valid date
        assertTrue(EventEndDate.isValidDate("2017-11-30")); // valid format
    }
}
