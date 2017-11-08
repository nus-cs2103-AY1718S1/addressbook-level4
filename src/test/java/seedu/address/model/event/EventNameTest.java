package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EventNameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(EventName.isValidName("^")); // only invalid characters
        assertFalse(EventName.isValidName("peter*")); // contains invalid characters
        assertFalse(EventName.isValidName("12345")); // numbers only
        assertFalse(EventName.isValidName("peter the 2nd")); // alphanumeric characters
        assertFalse(EventName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names

        // valid name
        assertTrue(EventName.isValidName("peter's jack")); // alphabets only
        assertTrue(EventName.isValidName("Capital Tan")); // with capital letters
        assertTrue(EventName.isValidName("")); // empty string
        assertTrue(EventName.isValidName(" ")); // spaces only
    }
}
