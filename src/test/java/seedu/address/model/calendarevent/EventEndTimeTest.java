package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author yilun-zhu
public class EventEndTimeTest {

    @Test
    public void isValidTime() {
        // invalid time
        assertFalse(EventEndTime.isValidTime("")); // empty string
        assertFalse(EventEndTime.isValidTime(" ")); // spaces only
        assertFalse(EventEndTime.isValidTime("0900")); // no colon in between
        assertFalse(EventEndTime.isValidTime("phone")); // non-numeric
        assertFalse(EventEndTime.isValidTime("p0:41")); // alphabets within digits
        assertFalse(EventEndTime.isValidTime("08 00")); // spaces within digits
        assertFalse(EventEndTime.isValidTime("99:45")); // valid format, invalid hour
        assertFalse(EventEndTime.isValidTime("10:61")); // valid format, invalid minute
        assertFalse(EventEndTime.isValidTime("9:45")); // hour should have 2 digits


        // valid time
        assertTrue(EventEndTime.isValidTime("10:24")); // valid format
    }
}
