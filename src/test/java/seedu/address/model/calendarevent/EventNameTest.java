package seedu.address.model.calendarevent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author yilun-zhu
public class EventNameTest {

    @Test
    public void isValidEventName() {
        // invalid name
        assertFalse(EventName.isValidEventName("")); // empty string
        assertFalse(EventName.isValidEventName(" ")); // spaces only
        assertFalse(EventName.isValidEventName("^")); // only non-alphanumeric characters
        assertFalse(EventName.isValidEventName("halloween*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(EventName.isValidEventName("halloween party")); // alphabets only
        assertTrue(EventName.isValidEventName("12345")); // numbers only
        assertTrue(EventName.isValidEventName("peter the 2nd")); // alphanumeric characters
        assertTrue(EventName.isValidEventName("Capital Halloween")); // with capital letters
        assertTrue(EventName.isValidEventName("David Roger Jackson Ray Jr 2nd Birthday")); // long names
    }
}
