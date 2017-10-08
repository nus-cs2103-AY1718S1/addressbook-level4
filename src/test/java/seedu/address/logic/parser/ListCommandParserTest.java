package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;


public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_listAddress_returnsListCommand() {
        assertParseSuccess(parser, " address", new ListCommand(ListCommand.ATTRIBUTE_ADDRESS));
    }

    @Test
    public void parse_listEmail_returnsListCommand() {
        assertParseSuccess(parser, " email", new ListCommand(ListCommand.ATTRIBUTE_EMAIL));
    }

    @Test
    public void parse_listPhone_returnsListCommand() {
        assertParseSuccess(parser, " phone", new ListCommand(ListCommand.ATTRIBUTE_PHONE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "list asda", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }
}
