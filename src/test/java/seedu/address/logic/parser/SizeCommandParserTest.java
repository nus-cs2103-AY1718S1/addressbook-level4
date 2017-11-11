package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SizeCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SizeCommandParserTest {

    private SizeCommandParser parser = new SizeCommandParser();

    @Test
    public void parse_validArgs_returnsSizeCommand() {
        assertParseSuccess(parser, "1", new SizeCommand(1));
        assertParseSuccess(parser, "-1", new SizeCommand(-1));
        assertParseSuccess(parser, "", new SizeCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SizeCommand.MESSAGE_USAGE));
    }
}
