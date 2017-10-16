package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class EventNameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(EventName.isValidName("")); // empty string
        assertFalse(EventName.isValidName(" ")); // spaces only
        assertFalse(EventName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(EventName.isValidName("The Big Walk*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(EventName.isValidName("hackathon")); // alphabets only
        assertTrue(EventName.isValidName("12345")); // numbers only
        assertTrue(EventName.isValidName("hackathon the 2nd")); // alphanumeric characters
        assertTrue(EventName.isValidName("Hackathon The First")); // with capital letters
        assertTrue(EventName.isValidName("The Longest Event Name Ever")); // long names
    }
}

