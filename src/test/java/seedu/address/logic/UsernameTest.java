package seedu.address.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author jelneo
public class UsernameTest {

    @Test
    public void isValidUsername() {
        // blank username
        assertFalse(Username.isValidUsername(""));
        assertFalse(Username.isValidUsername(" "));

        // username that contains illegal characters
        assertFalse(Username.isValidUsername("?username?"));
        assertFalse(Username.isValidUsername(".username123.%@$#"));

        // valid username
        assertTrue(Username.isValidUsername("username123"));
        assertTrue(Username.isValidUsername("UsErName123"));
        assertTrue(Username.isValidUsername("UsErName123__"));
    }

    @Test
    public void isValidUsernameLength() {
        // username that is less than length requirement
        assertFalse(Username.isValidUsernameLength(""));
        assertFalse(Username.isValidUsernameLength("1"));
        assertFalse(Username.isValidUsernameLength("Abc1_"));

        // username that meets the length requirement
        assertTrue(Username.isValidUsernameLength("123456"));
        assertTrue(Username.isValidUsernameLength("aBc123_oi"));
    }
}
