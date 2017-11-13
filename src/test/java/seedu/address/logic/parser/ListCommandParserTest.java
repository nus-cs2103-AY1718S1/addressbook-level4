package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;

//@@author tingtx
public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "TEST", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_returnListCommand() {
        ListCommand expectedListCommand = new ListCommand("");
        assertParseSuccess(parser, "     ", expectedListCommand);
    }

    @Test
    public void parse_validArgs_returnsListCommand() {
        ListCommand expectedListCommand = new ListCommand("g/TEST");
        assertParseSuccess(parser, " g/TEST", expectedListCommand);
    }


}
