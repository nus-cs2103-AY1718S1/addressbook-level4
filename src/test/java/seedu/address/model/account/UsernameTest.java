//@@author cqhchan
package seedu.address.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class UsernameTest {

    @Test
    public void isValidUsername() {
        // invalid name
        assertFalse(Username.isValidName("")); // empty string
        assertFalse(Username.isValidName(" ")); // spaces only
        assertFalse(Username.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Username.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Username.isValidName("peter jack")); // alphabets only
        assertTrue(Username.isValidName("12345")); // numbers only
        assertTrue(Username.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Username.isValidName("Capital Tan")); // with capital letters
        assertTrue(Username.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
