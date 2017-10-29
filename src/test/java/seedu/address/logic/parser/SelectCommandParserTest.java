package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.ui.BrowserSearchMode;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "-n 1",
                new SelectCommand(INDEX_FIRST_PERSON, BrowserSearchMode.GOOGLE_SEARCH_NAME));

        assertParseSuccess(parser, "-p 2",
                new SelectCommand(INDEX_SECOND_PERSON, BrowserSearchMode.GOOGLE_SEARCH_PHONE));

        assertParseSuccess(parser, "-e 2",
                new SelectCommand(INDEX_SECOND_PERSON, BrowserSearchMode.GOOGLE_SEARCH_EMAIL));

        assertParseSuccess(parser, "-a 3",
                new SelectCommand(INDEX_THIRD_PERSON, BrowserSearchMode.GOOGLE_SEARCH_ADDRESS));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "5", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "--", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "--a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-n5", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-f", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, SelectCommand.PREFIX_SELECT_SEARCH_EMAIL + " 0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, SelectCommand.PREFIX_SELECT_SEARCH_ADDRESS + " -1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
