package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.person.SortCommand;
import seedu.address.logic.parser.person.SortCommandParser;
//@@author Alim95
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidArgThrowsParseException() {
        assertParseFailure(parser, "home address",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsSortCommand() {
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
