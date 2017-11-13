package seedu.address.logic.parser;

import static guitests.guihandles.MainWindowHandle.TEST_PASSWORD;
import static guitests.guihandles.MainWindowHandle.TEST_USERNAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Password.MESSAGE_PASSWORD_LENGTH_CONSTRAINTS;
import static seedu.address.logic.Username.MESSAGE_USERNAME_CHARACTERS_CONSTRAINTS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.LoginCommandParser.EMPTY_PASSWORD_MESSAGE;
import static seedu.address.logic.parser.LoginCommandParser.EMPTY_USERNAME_MESSAGE;
import static seedu.address.ui.LoginView.SEPARATOR;
import static seedu.address.ui.LoginView.setShowingLoginView;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.logic.commands.LoginCommand;

//@@author jelneo
public class LoginCommandParserTest {
    private static final String VALID_USERNAME = "User_1234";
    private static final String VALID_PASSWORD = "P@$$worD";
    private static final String GUI_LOGIN_ARGS = " " + "%1$s" + SEPARATOR + "%2$s" + SEPARATOR;

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void cliParse_validArgs_returnsLoginCommand() {
        setShowingLoginView(false);

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
    public void cliParse_invalidArgs() {
        setShowingLoginView(false);
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
        setShowingLoginView(true);

        try {
            LoginCommand expectedLoginCommand =
                    new LoginCommand(new Username(VALID_USERNAME), new Password(VALID_PASSWORD));

            assertParseSuccess(parser,
                    String.format(GUI_LOGIN_ARGS, VALID_USERNAME, VALID_PASSWORD), expectedLoginCommand);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }

    @Test
    public void guiParse_invalidArgs() {
        setShowingLoginView(true);

        // no username
        assertParseFailure(parser, String.format(GUI_LOGIN_ARGS, "", TEST_PASSWORD), EMPTY_USERNAME_MESSAGE);

        // no password
        assertParseFailure(parser, String.format(GUI_LOGIN_ARGS, TEST_USERNAME, ""), EMPTY_PASSWORD_MESSAGE);

        // no username and password
        assertParseFailure(parser, String.format(GUI_LOGIN_ARGS, "", ""), EMPTY_USERNAME_MESSAGE);
    }
}
