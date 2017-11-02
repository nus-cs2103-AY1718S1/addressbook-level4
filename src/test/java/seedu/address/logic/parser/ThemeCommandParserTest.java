package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ThemeCommand;

/**
 * @see DeleteCommandParserTest
 */
public class ThemeCommandParserTest {

    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new ThemeCommand(Index.fromOneBased(1)));

        assertParseSuccess(parser, "caspian", new ThemeCommand("caspian"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
    }
}
