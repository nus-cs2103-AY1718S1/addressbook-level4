package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_NEW_SAMPLE;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_SAMPLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_PASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.PasswordCommand;

//@@author syy94
public class PasswordCommandParserTest {
    private PasswordCommandParser parser = new PasswordCommandParser();

    @Test
    public void parse_missingPrefix() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PasswordCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_addPassModeTest() {
        // valid values test
        PasswordCommand expectedCommand = new PasswordCommand(new PasswordCommand.SetPass(PASSWORD_SAMPLE));
        String userInput = " " + PREFIX_PASS + PASSWORD_SAMPLE;

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_changePassModeTest() {
        PasswordCommand expectedCommand = new PasswordCommand(new PasswordCommand.ChangePass(PASSWORD_SAMPLE,
                PASSWORD_NEW_SAMPLE));
        String userInput = " " + PREFIX_PASS + PASSWORD_SAMPLE + " " + PREFIX_NEW_PASS + PASSWORD_NEW_SAMPLE;

        assertParseSuccess(parser, userInput, expectedCommand);

        // missing PREFIX_PASS
        userInput = " " + PREFIX_NEW_PASS + PASSWORD_NEW_SAMPLE;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PasswordCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_removePassModeTest() {
        PasswordCommand expectedCommand = new PasswordCommand(new PasswordCommand.ClearPass(PASSWORD_SAMPLE));
        String userInput = " " + PREFIX_PASS + PASSWORD_SAMPLE + " " + PREFIX_CLEAR_PASS;

        assertParseSuccess(parser, userInput, expectedCommand);

        // missing PREFIX_PASS
        userInput = " " + PREFIX_CLEAR_PASS;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PasswordCommand.MESSAGE_USAGE));
    }
}
