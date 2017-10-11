package seedu.address.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author jelneo
public class UsernameTest {

    @Test
    public void hasValidUsernameCharacters() {
        // blank username
        assertFalse(Username.hasValidUsernameCharacters(""));
        assertFalse(Username.hasValidUsernameCharacters(" "));

        // username that contains illegal characters
        assertFalse(Username.hasValidUsernameCharacters("?username?"));
        assertFalse(Username.hasValidUsernameCharacters(".username123.%@$#"));

        // valid username
        assertTrue(Username.hasValidUsernameCharacters("username123"));
        assertTrue(Username.hasValidUsernameCharacters("UsErName123"));
        assertTrue(Username.hasValidUsernameCharacters("UsErName123__"));
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
