package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

import com.google.common.hash.Hashing;

import seedu.address.logic.parser.ChangePasswordCommandParser;
import seedu.address.model.UserPrefs;

//@@author charlesgoh
/**
 * Tests for lock and unlock functionality. Covers implementation across userprefs and model classes
 */
public class ChangePasswordCommandTest {

    private static final String DEFAULT_PASSWORD = "password";
    private static final String DEFAULT_SECOND_PASSWORD = "password2";

    private UserPrefs userPrefs;
    private ChangePasswordCommandParser parser;

    @Before
    public void setUp() {
        userPrefs = new UserPrefs();
        parser = new ChangePasswordCommandParser();
    }

    @Test
    public void parseWrongArgumentsFailure() {
        // No arguments failure
        assertParseFailure(parser, ChangePasswordCommand
                .COMMAND_WORD, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePasswordCommand.MESSAGE_USAGE));

        assertParseFailure(parser, ChangePasswordCommand
                .COMMAND_ALIAS, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePasswordCommand.MESSAGE_USAGE));

        // Wrong number of arguments
        assertParseFailure(parser, ChangePasswordCommand
                .COMMAND_WORD + "one two?", String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePasswordCommand.MESSAGE_USAGE));

        // No prefixes
        assertParseFailure(parser, ChangePasswordCommand
                .COMMAND_WORD + "password password2 password3", String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePasswordCommand.MESSAGE_USAGE));
    }

    @Test
    public void testUserPrefsPasswords() {
        // Check original password
        String expectedPassword = getHashed256(DEFAULT_PASSWORD);
        assertTrue(userPrefs.getAddressBookEncryptedPassword().equals(expectedPassword));

        // Lock address book and check if state has changed
        userPrefs.setAddressBookEncryptedPassword(DEFAULT_SECOND_PASSWORD);
        expectedPassword = getHashed256(DEFAULT_SECOND_PASSWORD);
        assertTrue(userPrefs.getAddressBookEncryptedPassword().equals(expectedPassword));
    }


    /**
     * Helps to convert string to SHA256 counterpart
     */
    private String getHashed256(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }
}
