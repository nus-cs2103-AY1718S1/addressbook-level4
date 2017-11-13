//@@author majunting
package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void invalid_input_format () {
        // no input
        assertParseFailure(parser, " ", MESSAGE_INVALID_COMMAND_FORMAT + SortCommand.MESSAGE_USAGE);

        // input is not one of the attributes
        assertParseFailure(parser, "wrong input", MESSAGE_INVALID_COMMAND_FORMAT + SortCommand.MESSAGE_USAGE);
    }

    @Test
    public void valid_input_format () throws ParseException {
        SortCommand expectedCommand;
        SortCommand actualCommand;

        expectedCommand = new SortCommand(0);
        actualCommand = parser.parse("name");
        assertEquals(expectedCommand.getKeyword(), actualCommand.getKeyword());

        expectedCommand = new SortCommand(1);
        actualCommand = parser.parse("phone");
        assertEquals(expectedCommand.getKeyword(), actualCommand.getKeyword());

        expectedCommand = new SortCommand(2);
        actualCommand = parser.parse("email");
        assertEquals(expectedCommand.getKeyword(), actualCommand.getKeyword());

        expectedCommand = new SortCommand(3);
        actualCommand = parser.parse("address");
        assertEquals(expectedCommand.getKeyword(), actualCommand.getKeyword());
    }
}
//@@author
