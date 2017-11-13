//@@author huiyiiih
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
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnsSortCommand() {
        //SortCommand exp
        assertParseSuccess(parser, "name", new SortCommand("name"));
        assertParseSuccess(parser, "tag", new SortCommand("tag"));
        assertParseSuccess(parser, "position", new SortCommand("position"));
        assertParseSuccess(parser, "company", new SortCommand("company"));
        assertParseSuccess(parser, "priority", new SortCommand("priority"));
    }
}
//@@author
