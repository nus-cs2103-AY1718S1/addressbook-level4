package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author yilun-zhu
public class EventStartDateTest {

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(EventStartDate.isValidDate("")); // empty string
        assertFalse(EventStartDate.isValidDate(" ")); // spaces only
        assertFalse(EventStartDate.isValidDate("20170809")); // no dash in between
        assertFalse(EventStartDate.isValidDate("phone")); // non-numeric
        assertFalse(EventStartDate.isValidDate("9011-p0-41")); // alphabets within digits
        assertFalse(EventStartDate.isValidDate("9312 1534")); // spaces within digits
        assertFalse(EventEndDate.isValidDate("2017-13-20")); // invalid month
        assertFalse(EventEndDate.isValidDate("2017-12-32")); // invalid day


        // valid date
        assertTrue(EventStartDate.isValidDate("2017-11-30")); // valid format
    }
}
