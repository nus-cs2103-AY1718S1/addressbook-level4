//@@author Houjisan
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_name_success() throws Exception {
        String userInput = "name";
        SortCommand expectedCommand = new SortCommand("name", false, false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nameIgnoreFav_success() throws Exception {
        String userInput = "name -ignorefav";
        SortCommand expectedCommand = new SortCommand("name", true, false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nameReverseOrder_success() throws Exception {
        String userInput = "name -reverse";
        SortCommand expectedCommand = new SortCommand("name", false, true);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nameIgnoreFavReverseOrder_success() throws Exception {
        String userInput = "name -ignorefav -reverse";
        SortCommand expectedCommand = new SortCommand("name", true, true);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, SortCommand.COMMAND_WORD, expectedMessage);
    }
}
