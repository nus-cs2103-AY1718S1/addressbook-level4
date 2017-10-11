package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.logic.commands.LoginCommand;

//@@author jelneo
public class LoginCommandParserTest {

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_validArgs_returnsLoginCommand() {
        String validUsername = "User_1234";
        String validPassword = "P@$$worD";

        try {
            LoginCommand expectedLoginCommand =
                    new LoginCommand(new Username(validUsername), new Password(validPassword));

            //no leading and trailing whitespaces
            assertParseSuccess(parser, validUsername + " " + validPassword, expectedLoginCommand);

            // with leading and trailing whitespaces
            assertParseSuccess(parser, validUsername + " " + validPassword + " ", expectedLoginCommand);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }

    @Test
    public void parse_invalidArgs_returnsLoginCommand() {
        assertParseFailure(parser, "    ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }

}
