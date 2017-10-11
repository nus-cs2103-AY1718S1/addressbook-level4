package seedu.address.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author jelneo
public class PasswordTest {

    @Test
    public void isValidPasswordLength() {
        // password does not meet length requirements
        assertFalse(Password.isValidPasswordLength(""));
        assertFalse(Password.isValidPasswordLength("_"));
        assertFalse(Password.isValidPasswordLength("pwd_1"));

        // password meets length requirement
        assertTrue(Password.isValidPasswordLength("abcde12345"));
        assertTrue(Password.isValidPasswordLength("ABCE1234!@#"));
    }

    @Test
    public void hasValidPasswordCharacters() {
        // blank password
        assertFalse(Password.isValidPasswordLength(""));
        assertFalse(Password.isValidPasswordLength(" "));

        // password contains illegal characters
        assertFalse(Password.hasValidPasswordCharacters("?what?"));
        assertFalse(Password.hasValidPasswordCharacters("?what?The//"));
        assertFalse(Password.hasValidPasswordCharacters("?what?The][]"));

        // valid password
        assertTrue(Password.hasValidPasswordCharacters("password123"));
        assertTrue(Password.hasValidPasswordCharacters("password_-#$"));
        assertTrue(Password.hasValidPasswordCharacters("PASSword_-#$"));
    }

}
