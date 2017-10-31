package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HeaderTest {
    @Test
    public void isValidHeader() {
        // invalid header
        assertFalse(Header.isValidHeader("")); // empty string
        assertFalse(Header.isValidHeader(" ")); // spaces only
        assertFalse(Header.isValidHeader("^")); // only non-alphanumeric characters
        assertFalse(Header.isValidHeader("lunch*")); // contains non-alphanumeric characters

        // valid header
        assertTrue(Header.isValidHeader("project meeting")); // alphabets only
        assertTrue(Header.isValidHeader("12345")); // numbers only
        assertTrue(Header.isValidHeader("travel on the 2nd")); // alphanumeric characters
        assertTrue(Header.isValidHeader("NUS conference")); // with capital letters
        assertTrue(Header.isValidHeader("Meet with David Roger Jackson Ray Jr 2nd")); // long header
    }
}
