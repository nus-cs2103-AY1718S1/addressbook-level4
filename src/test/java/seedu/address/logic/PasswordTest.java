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
    public void isValidPassword() {
        // blank password
        assertFalse(Password.isValidPasswordLength(""));
        assertFalse(Password.isValidPasswordLength(" "));

        // password contains illegal characters
        assertFalse(Password.isValidPassword("?what?"));
        assertFalse(Password.isValidPassword("?what?The//"));
        assertFalse(Password.isValidPassword("?what?The][]"));

        // valid password
        assertTrue(Password.isValidPassword("password123"));
        assertTrue(Password.isValidPassword("password_-#$"));
        assertTrue(Password.isValidPassword("PASSword_-#$"));
    }

}
