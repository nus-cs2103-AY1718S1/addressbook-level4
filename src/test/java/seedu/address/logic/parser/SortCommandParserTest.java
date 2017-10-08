package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "home address",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // no leading and trailing whitespaces
        assertParseSuccess(parser, "phone", new SortCommand("phone"));

        // leading and trailing whitespaces
        assertParseSuccess(parser, "   phone   ", new SortCommand("phone"));

        // upper and lower cases
        assertParseSuccess(parser, "pHoNe", new SortCommand("phone"));

        // sort for name
        assertParseSuccess(parser, "name", new SortCommand("name"));

        // sort for email
        assertParseSuccess(parser, "email", new SortCommand("email"));

        // sort for address
        assertParseSuccess(parser, "address", new SortCommand("address"));
    }
}
