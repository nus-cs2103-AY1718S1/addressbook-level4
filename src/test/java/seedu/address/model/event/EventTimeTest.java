package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class EventTimeTest {

    @Test
    public void isValidTime() {
        // invalid name
        assertFalse(EventTime.isValidTime("")); // empty string
        assertFalse(EventTime.isValidTime(" ")); // spaces only
        assertFalse(EventTime.isValidTime("^")); // only non-alphanumeric characters
        assertFalse(EventTime.isValidTime("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(EventTime.isValidTime("1230pm")); // time with pm
        assertTrue(EventTime.isValidTime("830am")); // time with am
    }
}
