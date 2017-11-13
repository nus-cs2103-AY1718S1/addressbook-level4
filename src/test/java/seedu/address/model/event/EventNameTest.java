package seedu.address.model.event;
// @@author HuWanqing
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EventNameTest {
    @Test
    public void isValidEventName() {
        // invalid name
        assertFalse(EventName.isValidName("")); // empty string
        assertFalse(EventName.isValidName(" ")); // spaces only
        assertFalse(EventName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(EventName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(EventName.isValidName("meeting")); // alphabets only
        assertTrue(EventName.isValidName("12345")); // numbers only
        assertTrue(EventName.isValidName("meeting 1")); // alphanumeric characters
        assertTrue(EventName.isValidName("First Meeting")); // with capital letters
        assertTrue(EventName.isValidName("A very important meeting tomorrow")); // long names
    }
}
