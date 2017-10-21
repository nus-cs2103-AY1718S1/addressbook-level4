package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.ListCommand;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;


public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_listLocation_returnsListCommand() {
        assertParseSuccess(parser, " location", new ListCommand(ListCommand.LOCATION_KEYWORD));
    }

    @Test
    public void parse_listModule_returnsListCommand() {
        assertParseSuccess(parser, " module", new ListCommand(ListCommand.MODULE_KEYWORD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "list asda", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }
}
