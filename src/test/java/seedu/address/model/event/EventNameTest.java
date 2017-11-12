package seedu.address.model.event;

//@@author chernghann
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EventNameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(EventName.isValidName("")); // empty string
        assertFalse(EventName.isValidName(" ")); // spaces only
        assertFalse(EventName.isValidName("peter*")); // contains not allowed non-alphanumeric characters

        // valid name
        assertTrue(EventName.isValidName("peter jack")); // alphabets only
        assertTrue(EventName.isValidName("12345")); // numbers only
        assertTrue(EventName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(EventName.isValidName("Capital Tan")); // with capital letters
        assertTrue(EventName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(EventName.isValidName("EE2020's Finals")); // special characters
    }
}
