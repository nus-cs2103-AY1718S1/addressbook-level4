package seedu.address.logic.parser;

import static guitests.guihandles.MainWindowHandle.TEST_PASSWORD;
import static guitests.guihandles.MainWindowHandle.TEST_USERNAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Password.MESSAGE_PASSWORD_LENGTH_CONSTRAINTS;
import static seedu.address.logic.Username.MESSAGE_USERNAME_CHARACTERS_CONSTRAINTS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.LoginCommandParser.EMPTY_PASSWORD_MESSAGE;
import static seedu.address.logic.parser.LoginCommandParser.EMPTY_USERNAME_MESSAGE;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jelneo
public class LoginCommandParserTest {
    private static final String VALID_USERNAME = "User_1234";
    private static final String VALID_PASSWORD = "P@$$worD";

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void cliParse_validArgs_returnsLoginCommand() {
        try {
            LoginCommand expectedLoginCommand =
                    new LoginCommand(new Username(VALID_USERNAME), new Password(VALID_PASSWORD));

            //no leading and trailing whitespaces
            assertParseSuccess(parser, VALID_USERNAME + " " + VALID_PASSWORD, expectedLoginCommand);

            // with leading and trailing whitespaces
            assertParseSuccess(parser, VALID_USERNAME + " " + VALID_PASSWORD + " ", expectedLoginCommand);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }

    @Test
    public void cliParse_invalidArgs_returnsLoginCommand() {
        // empty input
        assertParseFailure(parser, "    ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));

        // invalid username
        assertParseFailure(parser, "%&^%&$bhh" + " " + TEST_PASSWORD, MESSAGE_USERNAME_CHARACTERS_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, TEST_USERNAME + " " + "123", MESSAGE_PASSWORD_LENGTH_CONSTRAINTS);
    }

    @Test
    public void guiParse_validArgs_returnsLoginCommand() {
        try {
            LoginCommand expectedLoginCommand =
                    new LoginCommand(new Username(VALID_USERNAME), new Password(VALID_PASSWORD));

            assertParseSuccessForGui(parser, VALID_USERNAME, VALID_PASSWORD, expectedLoginCommand);

        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }

    @Test
    public void guiParse_invalidArgs_returnsLoginCommand() {
        // no username
        assertParseFailureForGui(parser, "", TEST_PASSWORD, EMPTY_USERNAME_MESSAGE);

        // no password
        assertParseFailureForGui(parser, TEST_USERNAME, "" , EMPTY_PASSWORD_MESSAGE);
    }


    /**
     * Asserts that the parsing of {@code userInput1} and {@code userInput2} by {@code parser} is
     * unsuccessful and the error message equals to {@code expectedMessage}.
     */
    public static void assertParseFailureForGui(LoginCommandParser parser, String userInput1, String userInput2,
                                          String expectedMessage) {
        try {
            parser.parse(userInput1, userInput2);
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that the parsing of {@code userInput1} and {@code userInput2} by {@code parser} is successful
     * and the command created equals to {@code expectedCommand}.
     */
    public static void assertParseSuccessForGui(LoginCommandParser parser, String userInput1, String userInput2,
                                          Command expectedCommand) {
        try {
            Command command = parser.parse(userInput1, userInput2);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
}
