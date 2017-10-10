package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.Name;

public class EventNameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("The Big Walk*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("hackathon")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("hackathon the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Hackathon The First")); // with capital letters
        assertTrue(Name.isValidName("The Longest Event Name Ever")); // long names
    }
}

