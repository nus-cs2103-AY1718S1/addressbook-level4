package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author yilun-zhu
public class EventStartTimeTest {

    @Test
    public void isValidTime() {
        // invalid time
        assertFalse(EventStartTime.isValidTime("")); // empty string
        assertFalse(EventStartTime.isValidTime(" ")); // spaces only
        assertFalse(EventStartTime.isValidTime("0900")); // no colon in between
        assertFalse(EventStartTime.isValidTime("phone")); // non-numeric
        assertFalse(EventStartTime.isValidTime("p0:41")); // alphabets within digits
        assertFalse(EventStartTime.isValidTime("08 00")); // spaces within digits
        assertFalse(EventStartTime.isValidTime("99:45")); // valid format, invalid hour
        assertFalse(EventStartTime.isValidTime("10:61")); // valid format, invalid minute
        assertFalse(EventStartTime.isValidTime("9:45")); // hour should have 2 digits


        // valid time
        assertTrue(EventStartTime.isValidTime("10:24")); // valid format
    }
}
